package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.model.Payment.PaymentStatus;
import com.pragma.payments.domain.port.PaymentProviderPort;
import com.pragma.payments.infrastructure.exception.PaymentProviderException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class FallbackPaymentProviderAdapter implements PaymentProviderPort {

    private static final Logger log = LoggerFactory.getLogger(FallbackPaymentProviderAdapter.class);
    private static final String FALLBACK_PROVIDER_NAME = "FallbackPaymentProvider";
    private static final double FALLBACK_FAILURE_RATE = 10.0;
    private static final Duration FALLBACK_TIMEOUT = Duration.ofSeconds(5);

    private final CircuitBreaker circuitBreaker;

    public FallbackPaymentProviderAdapter(CircuitBreakerRegistry circuitBreakerRegistry) {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(FALLBACK_FAILURE_RATE)
                .waitDurationInOpenState(Duration.ofSeconds(15))
                .slidingWindowSize(5)
                .minimumNumberOfCalls(2)
                .permittedNumberOfCallsInHalfOpenState(2)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
        
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker(FALLBACK_PROVIDER_NAME, config);
        
        log.info("FallbackPaymentProviderAdapter inicializado");
    }

    @Override
    public Mono<Payment> processPayment(Payment payment, Map<String, String> providerMetadata) {
        log.info("Procesando pago con proveedor FALLBACK: {}", payment.transactionId());
        
        return Mono.fromCallable(() -> simulateFallbackProviderCall(payment, providerMetadata))
                .subscribeOn(Schedulers.boundedElastic())
                .timeout(FALLBACK_TIMEOUT)
                .transformDeferred(mono -> circuitBreaker.executeMono(mono))
                .doOnSuccess(result -> log.info("Pago procesado via fallback: {}", result.providerTransactionId()))
                .doOnError(error -> log.error("Error en proveedor fallback: {}", error.getMessage()))
                .onErrorResume(error -> {
                    log.error("Proveedor fallback también falló, almacenando para reprocesamiento");
                    return storeForLaterRetry(payment, providerMetadata, error);
                });
    }

    @Override
    public Mono<Payment> rollbackPayment(Payment payment, Map<String, String> providerMetadata) {
        log.info("Ejecutando rollback via fallback para: {}", payment.transactionId());
        
        if (payment.providerTransactionId() == null) {
            return Mono.empty();
        }
        
        return Mono.fromCallable(() -> simulateFallbackRollback(payment, providerMetadata))
                .subscribeOn(Schedulers.boundedElastic())
                .timeout(FALLBACK_TIMEOUT)
                .doOnSuccess(result -> log.info("Rollback via fallback exitoso: {}", result.providerTransactionId()))
                .doOnError(error -> log.warn("Rollback fallback falló, marcando para revisión manual: {}", error.getMessage()))
                .onErrorResume(error -> Mono.just(payment.withStatus(PaymentStatus.PENDING_REVIEW)));
    }

    @Override
    public Mono<Boolean> healthCheck() {
        log.debug("Ejecutando health check del proveedor fallback");
        
        return Mono.fromCallable(() -> {
            simulateLatency(30);
            boolean isHealthy = ThreadLocalRandom.current().nextDouble() > 0.15;
            if (!isHealthy) {
                throw new RuntimeException("Proveedor fallback no disponible");
            }
            return true;
        })
        .subscribeOn(Schedulers.boundedElastic())
        .timeout(Duration.ofSeconds(2))
        .doOnSuccess(result -> log.debug("Health check fallback exitoso: {}", result))
        .doOnError(error -> log.warn("Health check fallback fallido: {}", error.getMessage()));
    }

    @Override
    public String getProviderName() {
        return FALLBACK_PROVIDER_NAME;
    }

    private Payment simulateFallbackProviderCall(Payment payment, Map<String, String> metadata) {
        simulateLatency(300);
        
        boolean shouldFail = ThreadLocalRandom.current().nextDouble() < 0.15;
        
        if (shouldFail) {
            throw new PaymentProviderException("Proveedor fallback no disponible temporalmente");
        }
        
        String providerTransactionId = "FALLBACK-" + UUID.randomUUID().toString();
        String providerName = FALLBACK_PROVIDER_NAME;
        
        return payment
                .withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails(providerTransactionId, providerName);
    }

    private Payment simulateFallbackRollback(Payment payment, Map<String, String> metadata) {
        simulateLatency(100);
        
        boolean shouldFail = ThreadLocalRandom.current().nextDouble() < 0.1;
        
        if (shouldFail) {
            throw new PaymentProviderException("Error en rollback fallback");
        }
        
        return payment.withStatus(PaymentStatus.ROLLED_BACK);
    }

    private Mono<Payment> storeForLaterRetry(Payment payment, Map<String, String> metadata, Throwable error) {
        log.warn("Almacenando pago {} para reprocesamiento posterior: {}", 
                payment.transactionId(), error.getMessage());
        
        return Mono.just(payment.withStatus(PaymentStatus.PENDING_REVIEW));
    }

    private void simulateLatency(long maxMillis) {
        try {
            long latency = ThreadLocalRandom.current().nextLong(20, maxMillis);
            Thread.sleep(latency);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PaymentProviderException("Llamada fallback interrumpida", e);
        }
    }

    public CircuitBreaker getCircuitBreaker() {
        return circuitBreaker;
    }

    public boolean isAvailable() {
        return circuitBreaker.getState() != CircuitBreaker.State.OPEN;
    }
}