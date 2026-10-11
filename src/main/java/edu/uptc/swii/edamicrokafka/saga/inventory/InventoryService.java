package edu.uptc.swii.edamicrokafka.saga.inventory;

import edu.uptc.swii.edamicrokafka.saga.config.SagaTopicConfig;
import edu.uptc.swii.edamicrokafka.saga.events.InventoryFailedEvent;
import edu.uptc.swii.edamicrokafka.saga.events.InventoryReservedEvent;
import edu.uptc.swii.edamicrokafka.saga.events.PaymentProcessedEvent;
import edu.uptc.swii.edamicrokafka.saga.events.SagaEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository repository;

    @Autowired
    private SagaEventPublisher publisher;

    // Stock simulado en memoria por producto
    private static final int STOCK_INICIAL = 10;

    public void reserveInventory(PaymentProcessedEvent event) {
        Inventory inventory = new Inventory();
        inventory.setInventoryId("INV_" + event.orderId());
        inventory.setOrderId(event.orderId());
        inventory.setProductId(event.productId());
        inventory.setQuantity(event.quantity());

        // Simulación de fallo aleatorio (30%) o por cantidad
        boolean falla = new Random().nextInt(10) < 3 || event.quantity() > STOCK_INICIAL;

        if (falla) {
            inventory.setStatus("FAILED");
            repository.save(inventory);
            publisher.publish(SagaTopicConfig.INVENTORY_FAILED, event.orderId(),
                    new InventoryFailedEvent(event.orderId(), event.productId(),
                            event.quantity(), "Stock insuficiente o fallo simulado"));
            System.out.println("Inventario FALLIDO para orden " + event.orderId());
        } else {
            inventory.setStatus("RESERVED");
            repository.save(inventory);
            publisher.publish(SagaTopicConfig.INVENTORY_RESERVED, event.orderId(),
                    new InventoryReservedEvent(event.orderId(), event.productId(), event.quantity()));
            System.out.println("Inventario RESERVADO para orden " + event.orderId());
        }
    }
}