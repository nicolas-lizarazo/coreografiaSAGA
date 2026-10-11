package edu.uptc.swii.edamicrokafka.saga.events;

import edu.uptc.swii.edamicrokafka.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SagaEventPublisher {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publish(String topic, String orderId, Object event) {
        kafkaTemplate.send(topic, orderId, JsonUtils.toJson(event));
    }
}