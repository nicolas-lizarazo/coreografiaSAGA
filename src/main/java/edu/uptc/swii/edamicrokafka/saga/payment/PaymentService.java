package edu.uptc.swii.edamicrokafka.saga.payment;

import edu.uptc.swii.edamicrokafka.saga.config.SagaTopicConfig;
import edu.uptc.swii.edamicrokafka.saga.events.OrderCreatedEvent;
import edu.uptc.swii.edamicrokafka.saga.events.PaymentFailedEvent;
import edu.uptc.swii.edamicrokafka.saga.events.PaymentProcessedEvent;
import edu.uptc.swii.edamicrokafka.saga.events.SagaEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private SagaEventPublisher publisher;

    public void processPayment(OrderCreatedEvent event) {
        Payment payment = new Payment();
        payment.setPaymentId("PAY_" + event.orderId());
        payment.setOrderId(event.orderId());
        payment.setAmount(event.amount());

        // Regla de negocio: montos > 1000 fallan
        if (event.amount() > 1000) {
            payment.setStatus("FAILED");
            repository.save(payment);

            publisher.publish(SagaTopicConfig.PAYMENT_FAILED, event.orderId(),
                    new PaymentFailedEvent(event.orderId(), "Monto excede el límite permitido"));
            System.out.println("Pago FALLIDO para orden " + event.orderId() + " (monto=" + event.amount() + ")");
        } else {
            payment.setStatus("PROCESSED");
            repository.save(payment);

            publisher.publish(SagaTopicConfig.PAYMENT_PROCESSED, event.orderId(),
                    new PaymentProcessedEvent(event.orderId(), payment.getPaymentId(),
                            event.productId(), event.quantity(), event.amount()));
            System.out.println("Pago PROCESADO para orden " + event.orderId());
        }
    }

    // Compensación: reembolsar si el inventario falla después
    public void refund(String orderId, String reason) {
        repository.findByOrderId(orderId).ifPresent(payment -> {
            if ("PROCESSED".equals(payment.getStatus())) {
                payment.setStatus("REFUNDED");
                repository.save(payment);
                System.out.println("Pago REEMBOLSADO para orden " + orderId + ": " + reason);
            }
        });
    }
}