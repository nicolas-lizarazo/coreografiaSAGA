package edu.uptc.swii.edamicrokafka.saga.payment;

import edu.uptc.swii.edamicrokafka.saga.config.SagaTopicConfig;
import edu.uptc.swii.edamicrokafka.saga.events.InventoryFailedEvent;
import edu.uptc.swii.edamicrokafka.saga.events.OrderCreatedEvent;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {

    private static final String GROUP = "payment-saga-group";

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = SagaTopicConfig.ORDER_CREATED, groupId = GROUP)
    public void onOrderCreated(String message) {
        OrderCreatedEvent e = JsonUtils.fromJson(message, OrderCreatedEvent.class);
        paymentService.processPayment(e);
    }

    @KafkaListener(topics = SagaTopicConfig.INVENTORY_FAILED, groupId = GROUP)
    public void onInventoryFailed(String message) {
        InventoryFailedEvent e = JsonUtils.fromJson(message, InventoryFailedEvent.class);
        paymentService.refund(e.orderId(), e.reason());
    }
}