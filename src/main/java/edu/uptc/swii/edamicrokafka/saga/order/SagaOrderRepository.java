package edu.uptc.swii.edamicrokafka.saga.order;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SagaOrderRepository extends MongoRepository<SagaOrder, String> {
}