package com.tron.apartmentservice.event.publisher;

import com.tron.apartmentservice.event.producer.PaymentKafkaProducer;
import com.tron.event.dto.PaymentCompletedEvent;
import com.tron.event.dto.PaymentCreatedEvent;
import com.tron.event.dto.PaymentRollbackEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ApartmentEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private Logger logger = LoggerFactory.getLogger(PaymentKafkaProducer.class);

    @Value("${kafka.topics.payment-created}")
    private String topic;

    public ApartmentEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentCreatedEvent(PaymentCreatedEvent event) {
        kafkaTemplate.send(topic,event.getSagaId(),event);
    }
}
