package edu.uptc.swii.edamicrokafka.saga.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saga/orders")
public class SagaOrderController {

    public record CreateOrderRequest(String customerId, String productId, int quantity, double amount) {}

    @Autowired
    private SagaOrderService service;

    @PostMapping
    public SagaOrder create(@RequestBody CreateOrderRequest req) {
        return service.createOrder(req.customerId(), req.productId(), req.quantity(), req.amount());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SagaOrder> get(@PathVariable String id) {
        SagaOrder order = service.findById(id);
        return order == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(order);
    }

    @GetMapping
    public List<SagaOrder> all() { return service.findAll(); }
}