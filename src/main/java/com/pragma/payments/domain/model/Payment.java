package com.pragma.payments.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Payment(
    UUID paymentId,
    String accountId,
    BigDecimal amount,
    String currency,
    PaymentStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String reference,
    String providerTransactionId,
    String providerName) {

    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED,
        ROLLBACK_INITIATED,
        ROLLED_BACK
    }

    public Payment {
        if (paymentId == null) {
            paymentId = UUID.randomUUID();
        }
        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency cannot be null or empty");
        }
        if (status == null) {
            status = PaymentStatus.PENDING;
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
    }

    public Payment withStatus(PaymentStatus newStatus) {
        return new Payment(
            this.paymentId,
            this.accountId,
            this.amount,
            this.currency,
            newStatus,
            this.createdAt,
            LocalDateTime.now(),
            this.reference,
            this.providerTransactionId,
            this.providerName
        );
    }

    public Payment withProviderDetails(String providerTransactionId, String providerName) {
        return new Payment(
            this.paymentId,
            this.accountId,
            this.amount,
            this.currency,
            this.status,
            this.createdAt,
            LocalDateTime.now(),
            this.reference,
            providerTransactionId,
            providerName
        );
    }
}