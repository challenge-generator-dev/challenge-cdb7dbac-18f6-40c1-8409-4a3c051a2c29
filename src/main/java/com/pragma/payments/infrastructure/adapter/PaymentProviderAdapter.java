package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.model.Payment.PaymentStatus;
import com.pragma.payments.domain.port.PaymentProviderPort;
import com.pragma.payments.infrastructure.exception.PaymentProviderException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
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
public class PaymentProviderAdapter implements PaymentProviderPort {

    private static final Logger log = LoggerFactory.getLogger(PaymentProviderAdapter.class);
    private static final String PROVIDER_NAME = "PrimaryPaymentProvider";
    private static final double FAILURE_RATE_THRESHOLD = 50.0;
    private static final int WAIT_DURATION_IN_OPEN_STATE = 30;
    private static final int SLIDING_WINDOW_SIZE = 10;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final Duration RETRY_WAIT_DURATION = Duration.ofMillis(500);

    private final CircuitBreaker circuitBreaker;
    private final Retry retry;

    public PaymentProviderAdapter(CircuitBreakerRegistry circuitBreakerRegistry, RetryRegistry retryRegistry) {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(FAILURE_RATE_THRESHOLD)
                .waitDurationInOpenState(Duration.ofSeconds(WAIT_DURATION_IN_OPEN_STATE))
                .slidingWindowSize(SLIDING_WINDOW_SIZE)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
        
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker(PROVIDER_NAME, config);
        
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(MAX_RETRY_ATTEMPTS)
                .waitDuration(RETRY_WAIT_DURATION)
                .retryExceptions(PaymentProviderException.class, RuntimeException.class)
                .ignoreExceptions(IllegalArgumentException.class)
                .build();
        
        this.retry = retryRegistry.retry(PROVIDER_NAME + "-retry", retryConfig);
        
        log.info("PaymentProviderAdapter inicializado con CircuitBreaker: {}", PROVIDER_NAME);
    }

    @Override
    public Mono<Payment> processPayment(Payment payment, Map<String, String> providerMetadata) {
        log.info("Procesando pago con proveedor primario: {}", payment.transactionId());
        
        return Mono.fromCallable(() -> simulateProviderCall(payment, providerMetadata))
                .subscribeOn(Schedulers.boundedElastic())
                .transformDeferred(mono -> circuitBreaker.executeMono(mono))
                .transformDeferred(mono -> retry.executeMono(mono))
                .doOnSuccess(result -> log.info("Pago procesado exitosamente por proveedor: {}", result.providerTransactionId()))
                .doOnError(error -> {
                    if (error instanceof CallNotPermittedException) {
                        log.error("Circuit breaker abierto, no se permiten llamadas");
                    } else {
                        log.error("Error procesando pago: {}", error.getMessage());
                    }
                })
                .onErrorResume(error -> {
                    if (error instanceof CallNotPermittedException) {
                        return Mono.error(new PaymentProviderException("Circuit breaker abierto", error));
                    }
                    return Mono.error(error);
                });
    }

    @Override
    public Mono<Payment> rollbackPayment(Payment payment, Map<String, String> providerMetadata) {
        log.info("Ejecutando rollback para transactionId: {}", payment.transactionId());
        
        if (payment.providerTransactionId() == null) {
            return Mono.empty();
        }
        
        return Mono.fromCallable(() -> simulateRollbackCall(payment, providerMetadata))
                .subscribeOn(Schedulers.boundedElastic())
                .transformDeferred(mono -> circuitBreaker.executeMono(mono))
                .doOnSuccess(result -> log.info("Rollback ejecutado: {}", result.providerTransactionId()))
                .doOnError(error -> log.error("Error en rollback: {}", error.getMessage()))
                .onErrorResume(error -> Mono.empty());
    }

    @Override
    public Mono<Boolean> healthCheck() {
        log.debug("Ejecutando health check del proveedor: {}", PROVIDER_NAME);
        
        return Mono.fromCallable(() -> {
            simulateLatency(50);
            boolean isHealthy = ThreadLocalRandom.current().nextDouble() > 0.3;
            if (!isHealthy) {
                throw new RuntimeException("Proveedor no disponible");
            }
            return true;
        })
        .subscribeOn(Schedulers.boundedElastic())
        .timeout(Duration.ofSeconds(3))
        .doOnSuccess(result -> log.debug("Health check exitoso: {}", result))
        .doOnError(error -> log.warn("Health check fallido: {}", error.getMessage()));
    }

    @Override
    public String getProviderName() {
        return PROVIDER_NAME;
    }

    private Payment simulateProviderCall(Payment payment, Map<String, String> metadata) {
        simulateLatency(200);
        
        boolean shouldFail = ThreadLocalRandom.current().nextDouble() < 0.4;
        
        if (shouldFail) {
            throw new PaymentProviderException("Error simulado del proveedor de pagos");
        }
        
        String providerTransactionId = UUID.randomUUID().toString();
        String providerName = PROVIDER_NAME;
        
        return payment
                .withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails(providerTransactionId, providerName);
    }

    private Payment simulateRollbackCall(Payment payment, Map<String, String> metadata) {
        simulateLatency(150);
        
        boolean shouldFail = ThreadLocalRandom.current().nextDouble() < 0.2;
        
        if (shouldFail) {
            throw new PaymentProviderException("Error en rollback del proveedor");
        }
        
        return payment.withStatus(PaymentStatus.ROLLED_BACK);
    }

    private void simulateLatency(long maxMillis) {
        try {
            long latency = ThreadLocalRandom.current().nextLong(50, maxMillis);
            Thread.sleep(latency);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PaymentProviderException("Llamada interrumpida", e);
        }
    }

    public CircuitBreaker getCircuitBreaker() {
        return circuitBreaker;
    }

    public Retry getRetry() {
        return retry;
    }

    public boolean isAvailable() {
        return circuitBreaker.getState() != CircuitBreaker.State.OPEN;
    }
}