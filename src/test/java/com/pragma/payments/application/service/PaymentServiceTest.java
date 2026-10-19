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
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
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
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PaymentService - Pruebas unitarias de tolerancia a fallos")
class PaymentServiceTest {

    @Mock
    private PaymentProviderAdapter paymentProviderAdapter;

    @Mock
    private FallbackPaymentProviderAdapter fallbackPaymentProviderAdapter;

    private CircuitBreakerRegistry circuitBreakerRegistry;
    private RetryRegistry retryRegistry;
    private MeterRegistry meterRegistry;
    private PaymentService paymentService;

    private Payment testPayment;

    @BeforeEach
    void setUp() {
        circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
        retryRegistry = RetryRegistry.ofDefaults();
        meterRegistry = new SimpleMeterRegistry();

        paymentService = new PaymentService(
            paymentProviderAdapter,
            fallbackPaymentProviderAdapter,
            circuitBreakerRegistry,
            retryRegistry,
            meterRegistry
        );

        testPayment = new Payment(
            UUID.randomUUID().toString(),
            "ORDER-001",
            new BigDecimal("100.00"),
            "USD",
            PaymentStatus.PENDING,
            null,
            null
        );
    }

    @Nested
    @DisplayName("Escenarios de procesamiento exitoso")
    class SuccessfulProcessing {

        @Test
        @DisplayName("Debería procesar pago exitosamente cuando el proveedor responde")
        void shouldProcessPaymentSuccessfully() {
            Payment completedPayment = testPayment.withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails("TXN-12345", "PRIMARY_PROVIDER");

            when(paymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.just(completedPayment));

            StepVerifier.create(paymentService.processPayment(testPayment, Map.of("channel", "WEB")))
                .assertNext(payment -> {
                    assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED);
                    assertThat(payment.providerTransactionId()).isEqualTo("TXN-12345");
                })
                .verifyComplete();

            verify(paymentProviderAdapter).processPayment(eq(testPayment), any(Map.class));
            verify(fallbackPaymentProviderAdapter, never()).processPayment(any(), any());
        }

        @Test
        @DisplayName("Debería usar fallback cuando el proveedor principal falla")
        void shouldUseFallbackWhenPrimaryProviderFails() {
            Payment fallbackPayment = testPayment.withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails("FALLBACK-TXN-67890", "FALLBACK_PROVIDER");

            when(paymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.error(new PaymentProviderException(
                    "Provider unavailable", "PRIMARY_PROVIDER", "TEMPORARY_UNAVAILABLE")));

            when(fallbackPaymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.just(fallbackPayment));

            StepVerifier.create(paymentService.processPayment(testPayment, Map.of("channel", "MOBILE")))
                .assertNext(payment -> {
                    assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED);
                    assertThat(payment.providerTransactionId()).isEqualTo("FALLBACK-TXN-67890");
                })
                .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Escenarios de falla del proveedor")
    class ProviderFailureScenarios {

        @Test
        @DisplayName("Debería manejar timeout del proveedor y marcar pago como FAILED")
        void shouldHandleProviderTimeout() {
            when(paymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.error(new PaymentProviderException(
                    "Connection timeout", "PRIMARY_PROVIDER", "TIMEOUT",
                    Map.of("retryCount", "3"), new RuntimeException("Connection timeout"))));

            when(fallbackPaymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.error(new PaymentProviderException(
                    "Fallback also failed", "FALLBACK_PROVIDER", "TEMPORARY_UNAVAILABLE"))));

            StepVerifier.create(paymentService.processPayment(testPayment, Map.of()))
                .expectError(PaymentProviderException.class)
                .verify();
        }

        @Test
        @DisplayName("Debería manejar error de circuit breaker abierto")
        void shouldHandleCircuitBreakerOpen() {
            PaymentProviderException circuitBreakerException = new PaymentProviderException(
                "Circuit breaker is open", "PRIMARY_PROVIDER", "CIRCUIT_BREAKER_OPEN");

            when(paymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.error(circuitBreakerException));

            when(fallbackPaymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.just(testPayment.withStatus(PaymentStatus.COMPLETED)));

            StepVerifier.create(paymentService.processPayment(testPayment, Map.of()))
                .assertNext(payment -> assertThat(payment.status()).isEqualTo(PaymentStatus.COMPLETED))
                .verifyComplete();
        }

        @Test
        @DisplayName("Debería fallar cuando ambos proveedores fallan consecutivamente")
        void shouldFailWhenBothProvidersFail() {
            PaymentProviderException primaryException = new PaymentProviderException(
                "Primary provider error", "PRIMARY_PROVIDER", "CONNECTION_ERROR");
            PaymentProviderException fallbackException = new PaymentProviderException(
                "Fallback provider error", "FALLBACK_PROVIDER", "CONNECTION_ERROR");

            when(paymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.error(primaryException));

            when(fallbackPaymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.error(fallbackException));

            StepVerifier.create(paymentService.processPayment(testPayment, Map.of()))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(PaymentProviderException.class);
                    PaymentProviderException pe = (PaymentProviderException) error;
                    assertThat(pe.getErrorCode()).isEqualTo("CONNECTION_ERROR");
                    assertThat(pe.isRetryable()).isTrue();
                })
                .verify();
        }
    }

    @Nested
    @DisplayName("Verificación de métricas y configuración de resiliencia")
    class ResilienceConfigurationTests {

        @Test
        @DisplayName("Debería registrar métricas de retry en el MeterRegistry")
        void shouldRegisterRetryMetrics() {
            Payment completedPayment = testPayment.withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails("TXN-METRICS", "PRIMARY_PROVIDER");

            when(paymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.just(completedPayment));

            paymentService.processPayment(testPayment, Map.of()).block();

            assertThat(meterRegistry.get("payments.retry.attempts").counter()).isNotNull();
        }

        @Test
        @DisplayName("Debería registrar métricas de circuit breaker en el MeterRegistry")
        void shouldRegisterCircuitBreakerMetrics() {
            Payment completedPayment = testPayment.withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails("TXN-CB", "PRIMARY_PROVIDER");

            when(paymentProviderAdapter.processPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.just(completedPayment));

            paymentService.processPayment(testPayment, Map.of()).block();

            assertThat(meterRegistry.get("payments.circuitbreaker.state").gauge()).isNotNull();
        }
    }

    @Nested
    @DisplayName("Escenarios de rollback")
    class RollbackScenarios {

        @Test
        @DisplayName("Debería ejecutar rollback correctamente cuando se solicita")
        void shouldExecuteRollbackSuccessfully() {
            Payment completedPayment = testPayment.withStatus(PaymentStatus.COMPLETED)
                .withProviderDetails("TXN-ROLLBACK", "PRIMARY_PROVIDER");

            when(paymentProviderAdapter.rollbackPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.just(completedPayment.withStatus(PaymentStatus.REFUNDED)));

            StepVerifier.create(paymentService.rollbackPayment(completedPayment, Map.of("reason", "CUSTOMER_REQUEST")))
                .assertNext(payment -> assertThat(payment.status()).isEqualTo(PaymentStatus.REFUNDED))
                .verifyComplete();
        }

        @Test
        @DisplayName("Debería manejar falla en rollback gracefully")
        void shouldHandleRollbackFailure() {
            when(paymentProviderAdapter.rollbackPayment(any(Payment.class), any(Map.class)))
                .thenReturn(Mono.error(new PaymentProviderException(
                    "Rollback failed", "PRIMARY_PROVIDER", "ROLLBACK_ERROR")));

            StepVerifier.create(paymentService.rollbackPayment(testPayment, Map.of()))
                .expectError(PaymentProviderException.class)
                .verify();
        }
    }

    @Nested
    @DisplayName("Validación de estado del proveedor")
    class HealthCheckTests {

        @Test
        @DisplayName("Debería retornar health check del proveedor primario")
        void shouldReturnPrimaryProviderHealth() {
            when(paymentProviderAdapter.healthCheck())
                .thenReturn(Mono.just(true));

            StepVerifier.create(paymentService.checkProviderHealth())
                .assertNext(result -> assertThat(result).isTrue())
                .verifyComplete();
        }

        @Test
        @DisplayName("Debería retornar false cuando health check falla")
        void shouldReturnFalseWhenHealthCheckFails() {
            when(paymentProviderAdapter.healthCheck())
                .thenReturn(Mono.just(false));

            StepVerifier.create(paymentService.checkProviderHealth())
                .assertNext(result -> assertThat(result).isFalse())
                .verifyComplete();
        }
    }
}