package com.pragma.payments.application.service;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.model.Payment.PaymentStatus;
import com.pragma.payments.infrastructure.adapter.FallbackPaymentProviderAdapter;
import com.pragma.payments.infrastructure.adapter.PaymentProviderAdapter;
import com.pragma.payments.infrastructure.exception.PaymentProviderException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private static final String PAYMENT_CIRCUIT_BREAKER_NAME = "paymentProvider";
    private static final String PAYMENT_RETRY_NAME = "paymentRetry";
    private static final Duration MAX_RESPONSE_TIME = Duration.ofSeconds(2);

    private final PaymentProviderAdapter primaryPaymentAdapter;
    private final FallbackPaymentProviderAdapter fallbackPaymentAdapter;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RetryRegistry retryRegistry;
    private final MeterRegistry meterRegistry;
    private final AtomicInteger retryCounter;
    private final AtomicInteger circuitBreakerCounter;

    public PaymentService(PaymentProviderAdapter primaryPaymentAdapter,
                          FallbackPaymentProviderAdapter fallbackPaymentAdapter,
                          CircuitBreakerRegistry circuitBreakerRegistry,
                          RetryRegistry retryRegistry,
                          MeterRegistry meterRegistry,
                          AtomicInteger retryCounter,
                          AtomicInteger circuitBreakerCounter) {
        this.primaryPaymentAdapter = primaryPaymentAdapter;
        this.fallbackPaymentAdapter = fallbackPaymentAdapter;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.retryRegistry = retryRegistry;
        this.meterRegistry = meterRegistry;
        this.retryCounter = retryCounter;
        this.circuitBreakerCounter = circuitBreakerCounter;
    }

    public Mono<Payment> processPayment(Payment payment, Map<String, String> metadata) {
        log.info("Iniciando procesamiento de pago para transactionId: {}", payment.transactionId());
        validatePayment(payment);

        Timer.Sample sample = Timer.start(meterRegistry);
        
        return primaryPaymentAdapter.processPayment(payment, metadata)
                .doOnSuccess(result -> {
                    log.info("Pago procesado exitosamente: {}", result.transactionId());
                    recordSuccessMetrics(sample);
                })
                .onErrorResume(error -> {
                    log.error("Error en proveedor primario, intentando fallback: {}", error.getMessage());
                    return handleFallback(payment, metadata, error);
                });
    }

    private Mono<Payment> handleFallback(Payment payment, Map<String, String> metadata, Throwable error) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(PAYMENT_CIRCUIT_BREAKER_NAME);
        
        if (circuitBreaker.getState() == CircuitBreaker.State.OPEN) {
            log.warn("Circuit breaker ABIERTO para proveedor primario, usando fallback directo");
            circuitBreakerCounter.incrementAndGet();
        }

        return fallbackPaymentAdapter.processPayment(payment, metadata)
                .doOnSuccess(result -> {
                    log.info("Pago procesado via fallback: {}", result.transactionId());
                })
                .doOnError(err -> {
                    log.error("Fallback también falló: {}", err.getMessage());
                    throw new PaymentProviderException("Todos los proveedores de pago fallaron", err);
                });
    }

    public Mono<Payment> rollbackPayment(Payment payment, Map<String, String> metadata) {
        log.info("Iniciando rollback para transactionId: {}", payment.transactionId());
        validatePaymentForRollback(payment);

        return primaryPaymentAdapter.rollbackPayment(payment, metadata)
                .switchIfEmpty(Mono.defer(() -> fallbackPaymentAdapter.rollbackPayment(payment, metadata)))
                .doOnSuccess(result -> log.info("Rollback exitoso para: {}", payment.transactionId()))
                .doOnError(error -> log.error("Rollback fallido: {}", error.getMessage()));
    }

    public Mono<Boolean> checkProviderHealth() {
        return primaryPaymentAdapter.healthCheck()
                .switchIfEmpty(Mono.just(false))
                .onErrorResume(error -> {
                    log.warn("Proveedor primario no disponible, verificando fallback: {}", error.getMessage());
                    return fallbackPaymentAdapter.healthCheck();
                });
    }

    private void validatePayment(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("El pago no puede ser null");
        }
        if (payment.transactionId() == null || payment.transactionId().isBlank()) {
            throw new IllegalArgumentException("El transactionId es requerido");
        }
        if (payment.amount() == null || payment.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        if (payment.status() != PaymentStatus.PENDING) {
            throw new IllegalArgumentException("Solo se pueden procesar pagos en estado PENDING");
        }
    }

    private void validatePaymentForRollback(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("El pago no puede ser null para rollback");
        }
        if (payment.providerTransactionId() == null || payment.providerTransactionId().isBlank()) {
            throw new IllegalArgumentException("No se puede hacer rollback sin providerTransactionId");
        }
        if (payment.status() != PaymentStatus.COMPLETED && payment.status() != PaymentStatus.FAILED) {
            throw new IllegalArgumentException("Solo se puede hacer rollback de pagos COMPLETED o FAILED");
        }
    }

    private void recordSuccessMetrics(Timer.Sample sample) {
        Timer.stop(meterRegistry.timer("payment.processing.time"), sample);
        meterRegistry.counter("payment.processed.success").increment();
    }

    private void recordFailureMetrics(Timer.Sample sample, Throwable error) {
        Timer.stop(meterRegistry.timer("payment.processing.time"), sample);
        meterRegistry.counter("payment.processed.failure").increment();
        log.error("Métricas de fallo registradas para error: {}", error.getMessage());
    }

    public Payment createPayment(BigDecimal amount, String currency, String description) {
        Payment payment = new Payment(
                UUID.randomUUID().toString(),
                amount,
                currency,
                PaymentStatus.PENDING,
                description,
                null,
                null,
                null
        );
        log.info("Pago creado: id={}, monto={} {}", payment.transactionId(), amount, currency);
        return payment;
    }

    public Mono<Payment> processPaymentWithRetry(Payment payment, Map<String, String> metadata) {
        Retry retry = retryRegistry.retry(PAYMENT_RETRY_NAME);
        
        return Mono.defer(() -> processPayment(payment, metadata))
                .retryWhen(retry)
                .doOnRetry(retryContext -> {
                    log.warn("Reintento {} ejecutado", retryContext.getRetryCount());
                    retryCounter.incrementAndGet();
                    meterRegistry.counter("payment.retry").increment();
                });
    }

    public CircuitBreaker getCircuitBreaker() {
        return circuitBreakerRegistry.circuitBreaker(PAYMENT_CIRCUIT_BREAKER_NAME);
    }

    public Retry getRetry() {
        return retryRegistry.retry(PAYMENT_RETRY_NAME);
    }
}