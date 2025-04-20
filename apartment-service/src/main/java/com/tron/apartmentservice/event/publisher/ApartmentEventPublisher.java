package com.tron.apartmentservice.event.publisher;

import com.tron.event.dto.PaymentCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ApartmentEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private Logger logger = LoggerFactory.getLogger(ApartmentEventPublisher.class);

    @Value("${kafka.topics.payment-created}")
    private String topic;

    public ApartmentEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentCreatedEvent(PaymentCreatedEvent event) {
        kafkaTemplate.send(topic,event.getSagaId(),event);
        logger.info("[APARTMENT-SERVICE] [PAYMENT-REQUEST] Sent to Kafka - SagaId: {}, ApartmentId: {}", event.getSagaId(), event.getApartmentId());
    }
}
