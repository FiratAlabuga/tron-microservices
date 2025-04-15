package com.tron.apartmentservice.event.producer;

import com.tron.event.dto.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.payment}")
    private String topic;

    public void sendPaymentCreatedEvent(PaymentCreatedEvent event) {
        kafkaTemplate.send("payment-created-topic", event.getSagaId(), event);
    }
}