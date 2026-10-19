package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.Payment;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface PaymentProviderPort {
    Mono<Payment> processPayment(Payment payment, Map<String, String> providerMetadata);

    Mono<Payment> rollbackPayment(Payment payment, Map<String, String> providerMetadata);

    Mono<Boolean> healthCheck();

    String getProviderName();
}