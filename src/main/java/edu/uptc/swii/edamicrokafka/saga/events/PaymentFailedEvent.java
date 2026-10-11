package edu.uptc.swii.edamicrokafka.saga.events;

public record PaymentFailedEvent(
        String orderId,
        String reason) {}