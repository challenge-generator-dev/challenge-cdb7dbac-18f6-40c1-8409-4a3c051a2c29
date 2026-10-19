package com.pragma.payments.infrastructure.controller;

import com.pragma.payments.application.service.PaymentService;
import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.model.Payment.PaymentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(
            @Valid @RequestBody PaymentRequest request) {
        Payment payment = new Payment(
                UUID.randomUUID().toString(),
                request.amount(),
                request.currency(),
                request.description(),
                PaymentStatus.PENDING,
                request.customerId(),
                null,
                null,
                null
        );
        
        Payment processedPayment = paymentService.processPayment(payment, request.metadata());
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PaymentResponse.fromPayment(processedPayment));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable String paymentId) {
        Payment payment = paymentService.getPayment(paymentId);
        
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(PaymentResponse.fromPayment(payment));
    }

    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<PaymentResponse> refundPayment(
            @PathVariable String paymentId,
            @RequestBody(required = false) RefundRequest request) {
        Payment payment = paymentService.getPayment(paymentId);
        
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }
        
        Payment refundedPayment = paymentService.refundPayment(payment, 
                request != null ? request.reason() : null);
        
        return ResponseEntity.ok(PaymentResponse.fromPayment(refundedPayment));
    }

    @GetMapping("/health/provider")
    public ResponseEntity<Map<String, Object>> providerHealth() {
        boolean healthy = paymentService.checkProviderHealth();
        
        return ResponseEntity.ok(Map.of(
                "healthy", healthy,
                "provider", "external-payment-gateway",
                "timestamp", System.currentTimeMillis()
        ));
    }

    public record PaymentRequest(
            @NotNull @Positive BigDecimal amount,
            @NotBlank String currency,
            @NotBlank String description,
            @NotBlank String customerId,
            Map<String, String> metadata
    ) {}

    public record RefundRequest(String reason) {}

    public record PaymentResponse(
            String paymentId,
            BigDecimal amount,
            String currency,
            String description,
            String status,
            String customerId,
            String providerTransactionId,
            String providerName,
            String createdAt,
            String updatedAt
    ) {
        public static PaymentResponse fromPayment(Payment payment) {
            return new PaymentResponse(
                    payment.id(),
                    payment.amount(),
                    payment.currency(),
                    payment.description(),
                    payment.status().name(),
                    payment.customerId(),
                    payment.providerTransactionId(),
                    payment.providerName(),
                    payment.createdAt() != null ? payment.createdAt().toString() : null,
                    payment.updatedAt() != null ? payment.updatedAt().toString() : null
            );
        }
    }
}