package edu.uptc.swii.edamicrokafka.saga.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class SagaTopicConfig {

    public static final String ORDER_CREATED       = "saga-order-created";
    public static final String PAYMENT_PROCESSED   = "saga-payment-processed";
    public static final String PAYMENT_FAILED      = "saga-payment-failed";
    public static final String INVENTORY_RESERVED  = "saga-inventory-reserved";
    public static final String INVENTORY_FAILED    = "saga-inventory-failed";

    @Bean public NewTopic orderCreatedTopic()      { return TopicBuilder.name(ORDER_CREATED).partitions(1).replicas(1).build(); }
    @Bean public NewTopic paymentProcessedTopic()  { return TopicBuilder.name(PAYMENT_PROCESSED).partitions(1).replicas(1).build(); }
    @Bean public NewTopic paymentFailedTopic()     { return TopicBuilder.name(PAYMENT_FAILED).partitions(1).replicas(1).build(); }
    @Bean public NewTopic inventoryReservedTopic() { return TopicBuilder.name(INVENTORY_RESERVED).partitions(1).replicas(1).build(); }
    @Bean public NewTopic inventoryFailedTopic()   { return TopicBuilder.name(INVENTORY_FAILED).partitions(1).replicas(1).build(); }
}