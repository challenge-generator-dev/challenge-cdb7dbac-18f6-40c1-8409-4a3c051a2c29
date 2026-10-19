package com.pragma.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import io.micrometer.core.instrument.MeterRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Health;
import com.pragma.payments.application.service.PaymentService;
import com.pragma.payments.infrastructure.adapter.PaymentProviderAdapter;
import com.pragma.payments.infrastructure.adapter.FallbackPaymentProviderAdapter;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@EnableAsync
public class Application {
    private final PaymentService paymentService;
    private final PaymentProviderAdapter paymentProviderAdapter;
    private final FallbackPaymentProviderAdapter fallbackPaymentProviderAdapter;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RetryRegistry retryRegistry;
    private final MeterRegistry meterRegistry;

    public Application(PaymentService paymentService,
                      PaymentProviderAdapter paymentProviderAdapter,
                      FallbackPaymentProviderAdapter fallbackPaymentProviderAdapter,
                      CircuitBreakerRegistry circuitBreakerRegistry,
                      RetryRegistry retryRegistry,
                      MeterRegistry meterRegistry) {
        this.paymentService = paymentService;
        this.paymentProviderAdapter = paymentProviderAdapter;
        this.fallbackPaymentProviderAdapter = fallbackPaymentProviderAdapter;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.retryRegistry = retryRegistry;
        this.meterRegistry = meterRegistry;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public HealthIndicator paymentProviderHealthIndicator() {
        return () -> {
            CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("paymentProvider");
            Retry retry = retryRegistry.retry("paymentProvider");

            boolean isCircuitBreakerHealthy = circuitBreaker.getState() != CircuitBreaker.State.FORCED_OPEN &&
                                             circuitBreaker.getState() != CircuitBreaker.State.DISABLED;
            boolean isRetryHealthy = retry.getMetrics().getNumberOfSuccessfulCallsWithoutRetryAttempt() > 0 ||
                                    retry.getMetrics().getNumberOfSuccessfulCallsWithRetryAttempt() > 0;

            if (isCircuitBreakerHealthy && isRetryHealthy) {
                return Health.up()
                    .withDetail("circuitBreaker", circuitBreaker.getState())
                    .withDetail("retrySuccessCount", retry.getMetrics().getNumberOfSuccessfulCallsWithRetryAttempt())
                    .withDetail("retryFailureCount", retry.getMetrics().getNumberOfFailedCallsWithRetryAttempt())
                    .build();
            } else {
                return Health.down()
                    .withDetail("circuitBreaker", circuitBreaker.getState())
                    .withDetail("retrySuccessCount", retry.getMetrics().getNumberOfSuccessfulCallsWithRetryAttempt())
                    .withDetail("retryFailureCount", retry.getMetrics().getNumberOfFailedCallsWithRetryAttempt())
                    .build();
            }
        };
    }

    @Bean
    public AtomicInteger paymentRetryCounter() {
        return meterRegistry.gauge("payment.retry.attempts", new AtomicInteger(0));
    }

    @Bean
    public AtomicInteger paymentCircuitBreakerCounter() {
        return meterRegistry.gauge("payment.circuitbreaker.state.changes", new AtomicInteger(0));
    }
}