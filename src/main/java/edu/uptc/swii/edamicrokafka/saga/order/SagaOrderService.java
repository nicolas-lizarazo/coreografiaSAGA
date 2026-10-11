package edu.uptc.swii.edamicrokafka.saga.order;

import edu.uptc.swii.edamicrokafka.saga.config.SagaTopicConfig;
import edu.uptc.swii.edamicrokafka.saga.events.OrderCreatedEvent;
import edu.uptc.swii.edamicrokafka.saga.events.SagaEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SagaOrderService {

    @Autowired
    private SagaOrderRepository repository;

    @Autowired
    private SagaEventPublisher publisher;

    public SagaOrder createOrder(String customerId, String productId, int quantity, double amount) {
        SagaOrder order = new SagaOrder();
        order.setCustomerId(customerId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setAmount(amount);
        order.setStatus(SagaOrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order = repository.save(order);

        publisher.publish(SagaTopicConfig.ORDER_CREATED, order.getId(),
                new OrderCreatedEvent(order.getId(), customerId, productId, quantity, amount));
        return order;
    }

    // Compensación: solo cancela si la orden sigue pendiente (evita reprocesar el mismo evento)
    public void cancel(String orderId, String reason) {
        repository.findById(orderId).ifPresent(order -> {
            if (order.getStatus() == SagaOrderStatus.PENDING) {
                order.setStatus(SagaOrderStatus.CANCELLED);
                order.setCancelReason(reason);
                repository.save(order);
                System.out.println("Orden " + orderId + " CANCELADA: " + reason);
            }
        });
    }

    public void confirm(String orderId) {
        repository.findById(orderId).ifPresent(order -> {
            if (order.getStatus() == SagaOrderStatus.PENDING) {
                order.setStatus(SagaOrderStatus.CONFIRMED);
                repository.save(order);
                System.out.println("Orden " + orderId + " CONFIRMADA");
            }
        });
    }

    public SagaOrder findById(String id) { return repository.findById(id).orElse(null); }

    public List<SagaOrder> findAll() { return repository.findAll(); }
}