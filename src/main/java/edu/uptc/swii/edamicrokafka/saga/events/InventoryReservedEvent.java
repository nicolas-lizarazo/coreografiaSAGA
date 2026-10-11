package edu.uptc.swii.edamicrokafka.saga.events;

public record InventoryReservedEvent(
        String orderId,
        String productId,
        int quantity) {}