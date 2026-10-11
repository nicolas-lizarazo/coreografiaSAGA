package edu.uptc.swii.edamicrokafka.saga.order;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "saga_orders")
public class SagaOrder {

    @Id
    private String id;
    private String customerId;
    private String productId;
    private int quantity;
    private double amount;
    private SagaOrderStatus status;
    private String cancelReason;
    private LocalDateTime createdAt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public SagaOrderStatus getStatus() { return status; }
    public void setStatus(SagaOrderStatus status) { this.status = status; }
    public String getCancelReason() { return cancelReason; }
    public void setCancelReason(String cancelReason) { this.cancelReason = cancelReason; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}