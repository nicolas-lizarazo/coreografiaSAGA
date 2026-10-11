package edu.uptc.swii.edamicrokafka.saga.inventory;

import edu.uptc.swii.edamicrokafka.saga.config.SagaTopicConfig;
import edu.uptc.swii.edamicrokafka.saga.events.PaymentProcessedEvent;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryConsumer {

    private static final String GROUP = "inventory-saga-group";

    @Autowired
    private InventoryService inventoryService;

    @KafkaListener(topics = SagaTopicConfig.PAYMENT_PROCESSED, groupId = GROUP)
    public void onPaymentProcessed(String message) {
        PaymentProcessedEvent e = JsonUtils.fromJson(message, PaymentProcessedEvent.class);
        inventoryService.reserveInventory(e);
    }
}