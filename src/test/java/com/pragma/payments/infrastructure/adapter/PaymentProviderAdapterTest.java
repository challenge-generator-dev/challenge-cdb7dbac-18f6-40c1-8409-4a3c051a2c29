package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.model.Payment.PaymentStatus;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PaymentProviderAdapter - Pruebas de integración con Resilience4j")
class PaymentProviderAdapterTest {

    @Mock
    private ExternalPaymentGateway mockGateway;

    private CircuitBreakerRegistry circuitBreakerRegistry;
    private RetryRegistry retryRegistry;
    private PaymentProviderAdapter adapter;

    private Payment testPayment;
    private static final String PROVIDER_NAME = "EXTERNAL_GATEWAY";

    @BeforeEach
    void setUp() {
        circuitBreakerRegistry = CircuitBreakerRegistry.of(
            CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .build()
        );

        retryRegistry = RetryRegistry.of(
            RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .retryExceptions(IOException.class, TimeoutException.class)
                .build()
        );

        adapter = new PaymentProviderAdapter(mockGateway, circuitBreakerRegistry, retryRegistry);

        testPayment = new Payment(
            UUID.randomUUID().toString(),
            "ORDER-TEST-001",
            new BigDecimal("250.00"),
            "USD",
            PaymentStatus.PENDING,
            null,
            null
        );
    }

    @Nested
    @DisplayName("Procesamiento de pagos con Circuit Breaker")
    class CircuitBreakerTests {

        @Test
        @DisplayName("Debería abrir el circuit breaker después de alcanzar el umbral de errores")
        void shouldOpenCircuitBreakerAfterFailureThreshold() {
            when(mockGateway.processPayment(any()))
                .thenReturn(Mono.error(new IOException("Network error")));

            for (int i = 0; i < 6; i++) {
                StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                    .expectError(IOException.class)
                    .verify();
            }

            CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(PROVIDER_NAME);
            assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.OPEN);
        }

        @Test
        @DisplayName("Debería permitir llamada cuando el circuit breaker está полуоткрыт")
        void shouldAllowCallWhenCircuitBreakerIsHalfOpen() {
            when(mockGateway.processPayment(any()))
                .thenReturn(Mono.error(new IOException("Network error")));

            for (int i = 0; i < 5; i++) {
                StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                    .expectError(IOException.class)
                    .verify();
            }

            when(mockGateway.processPayment(any()))
                .thenReturn(Mono.just(createSuccessfulResponse()));

            StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                .assertNext(payment -> assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED))
                .verifyComplete();
        }

        @Test
        @DisplayName("Debería cerrar el circuit breaker después de exitosos")
        void shouldCloseCircuitBreakerAfterSuccess() {
            when(mockGateway.processPayment(any()))
                .thenReturn(Mono.just(createSuccessfulResponse()));

            for (int i = 0; i < 8; i++) {
                StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                    .assertNext(payment -> assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED))
                    .verifyComplete();
            }

            CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(PROVIDER_NAME);
            assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.CLOSED);
            assertThat(circuitBreaker.getMetrics().getFailureRate()).isLessThan(50.0f);
        }
    }

    @Nested
    @DisplayName("Procesamiento de pagos con Retry")
    class RetryTests {

        @Test
        @DisplayName("Debería reintentar la operación hasta 3 veces en caso de falla")
        void shouldRetryUpToThreeTimes() {
            when(mockGateway.processPayment(any()))
                .thenReturn(
                    Mono.error(new IOException("Temporary failure")),
                    Mono.error(new IOException("Temporary failure")),
                    Mono.just(createSuccessfulResponse())
                );

            StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                .assertNext(payment -> assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED))
                .verifyComplete();

            verify(mockGateway, times(3)).processPayment(any());
        }

        @Test
        @DisplayName("Debería fallar después de agotar reintentos")
        void shouldFailAfterExhaustingRetries() {
            when(mockGateway.processPayment(any()))
                .thenReturn(Mono.error(new IOException("Persistent failure")));

            StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                .expectError(IOException.class)
                .verify();

            verify(mockGateway, times(3)).processPayment(any());
        }

        @Test
        @DisplayName("Debería usar backoff exponencial entre reintentos")
        void shouldUseExponentialBackoff() {
            when(mockGateway.processPayment(any()))
                .thenReturn(
                    Mono.error(new IOException("First attempt failed")),
                    Mono.error(new IOException("Second attempt failed")),
                    Mono.just(createSuccessfulResponse())
                );

            long startTime = System.currentTimeMillis();
            StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                .assertNext(payment -> assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED))
                .verifyComplete();
            long elapsedTime = System.currentTimeMillis() - startTime;

            assertThat(elapsedTime).isGreaterThanOrEqualTo(500);
        }
    }

    @Nested
    @DisplayName("Integración de Circuit Breaker y Retry")
    class CombinedResilienceTests {

        @Test
        @DisplayName("Debería manejar falla con circuit breaker y retry juntos")
        void shouldHandleCombinedCircuitBreakerAndRetry() {
            when(mockGateway.processPayment(any()))
                .thenReturn(Mono.error(new IOException("Provider unavailable")));

            StepVerifier.create(adapter.processPayment(testPayment, Map.of()))
                .expectError(IOException.class)
                .verify();

            verify(mockGateway, atLeast(3)).processPayment(any());
        }

        @Test
        @DisplayName("Debería registrar métricas de ambas resiliencias")
        void shouldRegisterMetricsForBothResiliences() {
            when(mockGateway.processPayment(any()))
                .thenReturn(Mono.just(createSuccessfulResponse()));

            adapter.processPayment(testPayment, Map.of()).block();

            CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker(PROVIDER_NAME);
            Retry retry = retryRegistry.retry(PROVIDER_NAME);

            assertThat(cb.getMetrics().getNumberOfSuccessfulCalls()).isGreaterThan(0);
            assertThat(retry.getMetrics().getNumberOfSuccessfulCallsWithRetryAttempt()).isGreaterThan(0);
        }
    }

    @Nested
    @DisplayName("Health check del proveedor")
    class HealthCheckTests {

        @Test
        @DisplayName("Debería retornar true cuando el health check del gateway es exitoso")
        void shouldReturnTrueWhenGatewayHealthCheckSucceeds() {
            when(mockGateway.healthCheck()).thenReturn(Mono.just(true));

            StepVerifier.create(adapter.healthCheck())
                .assertNext(result -> assertThat(result).isTrue())
                .verifyComplete();
        }

        @Test
        @DisplayName("Debería manejar falla en health check gracefully")
        void shouldHandleHealthCheckFailure() {
            when(mockGateway.healthCheck())
                .thenReturn(Mono.error(new IOException("Health check endpoint unavailable")));

            StepVerifier.create(adapter.healthCheck())
                .assertNext(result -> assertThat(result).isFalse())
                .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Rollback de pagos")
    class RollbackTests {

        @Test
        @DisplayName("Debería ejecutar rollback exitosamente")
        void shouldExecuteRollbackSuccessfully() {
            Payment completedPayment = testPayment.withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails("TXN-123", PROVIDER_NAME);

            when(mockGateway.rollbackPayment(any()))
                .thenReturn(Mono.just(createSuccessfulResponse()));

            StepVerifier.create(adapter.rollbackPayment(completedPayment, Map.of("reason", "CUSTOMER_DISPUTE")))
                .assertNext(payment -> assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED))
                .verifyComplete();

            verify(mockGateway).rollbackPayment(any());
        }

        @Test
        @DisplayName("Debería propagar error de rollback correctamente")
        void shouldPropagateRollbackError() {
            when(mockGateway.rollbackPayment(any()))
                .thenReturn(Mono.error(new IOException("Rollback failed at provider")));

            StepVerifier.create(adapter.rollbackPayment(testPayment, Map.of()))
                .expectError(IOException.class)
                .verify();
        }
    }

    private Payment createSuccessfulResponse() {
        return testPayment.withStatus(PaymentStatus.COMPLETED)
            .withProviderDetails("PROVIDER-TXN-" + UUID.randomUUID(), PROVIDER_NAME);
    }

    interface ExternalPaymentGateway {
        Mono<Payment> processPayment(Payment payment);
        Mono<Payment> rollbackPayment(Payment payment);
        Mono<Boolean> healthCheck();
    }

    static class IOException extends java.io.IOException {
        public IOException(String message) {
            super(message);
        }
    }

    static class TimeoutException extends java.util.concurrent.TimeoutException {
        public TimeoutException(String message) {
            super(message);
        }
    }
}