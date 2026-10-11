package edu.uptc.swii.edamicrokafka.saga.events;

public record OrderCreatedEvent(
        String orderId,
        String customerId,
        String productId,
        int quantity,
        double amount) {}