package edu.uptc.swii.edamicrokafka.saga.shipping;

import edu.uptc.swii.edamicrokafka.saga.config.SagaTopicConfig;
import edu.uptc.swii.edamicrokafka.saga.events.InventoryReservedEvent;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ShippingConsumer {

    private static final String GROUP = "shipping-saga-group";

    @Autowired
    private ShipmentRepository repository;

    @KafkaListener(topics = SagaTopicConfig.INVENTORY_RESERVED, groupId = GROUP)
    public void onInventoryReserved(String message) {
        InventoryReservedEvent e = JsonUtils.fromJson(message, InventoryReservedEvent.class);

        Shipment shipment = new Shipment();
        shipment.setShipmentId("SHIP_" + e.orderId());
        shipment.setOrderId(e.orderId());
        shipment.setProductId(e.productId());
        shipment.setQuantity(e.quantity());
        shipment.setStatus("SCHEDULED");
        repository.save(shipment);

        System.out.println("Envío PROGRAMADO para orden " + e.orderId());
    }
}