package edu.uptc.swii.edamicrokafka.saga.order;

import edu.uptc.swii.edamicrokafka.saga.config.SagaTopicConfig;
import edu.uptc.swii.edamicrokafka.saga.events.InventoryFailedEvent;
import edu.uptc.swii.edamicrokafka.saga.events.InventoryReservedEvent;
import edu.uptc.swii.edamicrokafka.saga.events.PaymentFailedEvent;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SagaOrderConsumer {

    private static final String GROUP = "order-saga-group";

    @Autowired
    private SagaOrderService orderService;

    @KafkaListener(topics = SagaTopicConfig.PAYMENT_FAILED, groupId = GROUP)
    public void onPaymentFailed(String message) {
        PaymentFailedEvent e = JsonUtils.fromJson(message, PaymentFailedEvent.class);
        orderService.cancel(e.orderId(), "Pago fallido: " + e.reason());
    }

    @KafkaListener(topics = SagaTopicConfig.INVENTORY_FAILED, groupId = GROUP)
    public void onInventoryFailed(String message) {
        InventoryFailedEvent e = JsonUtils.fromJson(message, InventoryFailedEvent.class);
        orderService.cancel(e.orderId(), "Inventario fallido: " + e.reason());
    }

    @KafkaListener(topics = SagaTopicConfig.INVENTORY_RESERVED, groupId = GROUP)
    public void onInventoryReserved(String message) {
        InventoryReservedEvent e = JsonUtils.fromJson(message, InventoryReservedEvent.class);
        orderService.confirm(e.orderId());
    }
}