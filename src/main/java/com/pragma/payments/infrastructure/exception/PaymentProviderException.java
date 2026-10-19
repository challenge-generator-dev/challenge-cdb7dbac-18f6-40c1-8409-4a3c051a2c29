package com.pragma.payments.infrastructure.exception;

import java.time.Instant;
import java.util.Map;

public class PaymentProviderException extends RuntimeException {

    private final String providerName;
    private final String errorCode;
    private final Instant timestamp;
    private final Map<String, String> metadata;

    public PaymentProviderException(String message, String providerName, String errorCode) {
        super(message);
        this.providerName = providerName;
        this.errorCode = errorCode;
        this.timestamp = Instant.now();
        this.metadata = Map.of();
    }

    public PaymentProviderException(String message, String providerName, String errorCode, Throwable cause) {
        super(message, cause);
        this.providerName = providerName;
        this.errorCode = errorCode;
        this.timestamp = Instant.now();
        this.metadata = Map.of();
    }

    public PaymentProviderException(String message, String providerName, String errorCode, 
                                     Map<String, String> metadata, Throwable cause) {
        super(message, cause);
        this.providerName = providerName;
        this.errorCode = errorCode;
        this.timestamp = Instant.now();
        this.metadata = metadata != null ? Map.copyOf(metadata) : Map.of();
    }

    public String getProviderName() {
        return providerName;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public boolean isRetryable() {
        return "TIMEOUT".equals(errorCode) || 
               "CONNECTION_ERROR".equals(errorCode) ||
               "TEMPORARY_UNAVAILABLE".equals(errorCode);
    }

    public boolean isCircuitBreakerFailure() {
        return "CIRCUIT_BREAKER_OPEN".equals(errorCode) ||
               "CIRCUIT_BREAKER_HALF_OPEN".equals(errorCode);
    }
}