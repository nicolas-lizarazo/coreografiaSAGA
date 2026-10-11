package edu.uptc.swii.edamicrokafka.saga.events;

public record PaymentProcessedEvent(
        String orderId,
        String paymentId,
        String productId,
        int quantity,
        double amount) {}