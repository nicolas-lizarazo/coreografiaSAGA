package edu.uptc.swii.edamicrokafka.saga.shipping;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, String> {
}