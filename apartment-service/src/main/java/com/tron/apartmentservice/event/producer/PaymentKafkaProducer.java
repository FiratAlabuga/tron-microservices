package com.tron.apartmentservice.event.producer;

import com.tron.event.PaymentEventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentKafkaProducer {
    private Logger logger = LoggerFactory.getLogger(PaymentKafkaProducer.class);
    // KafkaTemplate is a high-level abstraction for sending messages to Kafka topics
    private final KafkaTemplate<String, PaymentEventRequest> kafkaTemplate;

    @Value("${kafka.topic.payment}")
    private String topic;

    public PaymentKafkaProducer(KafkaTemplate<String, PaymentEventRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentEvent(PaymentEventRequest paymentEventRequest) {
        kafkaTemplate.send(topic, paymentEventRequest);
        logger.atInfo().log("Payment event sent to Kafka topic: {}", topic);
    }
}
