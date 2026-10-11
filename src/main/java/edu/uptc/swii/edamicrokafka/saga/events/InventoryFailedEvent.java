package edu.uptc.swii.edamicrokafka.saga.events;

public record InventoryFailedEvent(
        String orderId,
        String productId,
        int quantity,
        String reason) {}