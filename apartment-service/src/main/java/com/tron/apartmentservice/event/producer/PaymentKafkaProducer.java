package com.tron.apartmentservice.event.producer;

import com.tron.apartmentservice.config.ApartmentKafkaConfig;
import com.tron.event.PaymentEventRequest;
import com.tron.event.dto.PaymentCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentKafkaProducer {
    private Logger logger = LoggerFactory.getLogger(PaymentKafkaProducer.class);
    // KafkaTemplate is a high-level abstraction for sending messages to Kafka topics
    private final KafkaTemplate<String, PaymentCreatedEvent> kafkaTemplate;
    // The topic to which messages will be sent
    private final ApartmentKafkaConfig apartmentKafkaConfig;
    @Value("${kafka.topic.payment}")
    private String topic;

    public PaymentKafkaProducer(KafkaTemplate<String, PaymentCreatedEvent> kafkaTemplate, ApartmentKafkaConfig apartmentKafkaConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.apartmentKafkaConfig = apartmentKafkaConfig;
    }

    public ApartmentKafkaConfig getApartmentKafkaConfig() {
        return apartmentKafkaConfig;
    }

    //    public void sendPaymentEvent(PaymentEventRequest paymentEventRequest) {
//        kafkaTemplate.send(topic, paymentEventRequest);
//        logger.atInfo().log("Payment event sent to Kafka topic: {}", topic);
//    }
    public boolean sendPaymentEvent(PaymentCreatedEvent paymentCreatedEvent){
        try {
            kafkaTemplate.send(topic,paymentCreatedEvent.getSagaId(),paymentCreatedEvent);
            logger.info("[APARTMENT-SERVICE] [PAYMENT-REQUEST] Sent to Kafka - SagaId: {}, ApartmentId: {}", paymentCreatedEvent.getSagaId(), paymentCreatedEvent.getApartmentId());
        }catch (Exception e){
            logger.error("[APARTMENT-SERVICE] [PAYMENT-REQUEST] Failed - SagaId: {}, ApartmentId: {}, Error: {}", paymentCreatedEvent.getSagaId(), paymentCreatedEvent.getApartmentId(), e.getMessage());
            return false;
        }
        return true;
    }
}
