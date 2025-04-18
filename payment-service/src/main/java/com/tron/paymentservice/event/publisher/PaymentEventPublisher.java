package com.tron.paymentservice.event.publisher;

import com.tron.event.dto.PaymentCompletedEvent;
import com.tron.event.dto.PaymentRollbackEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentCompletedEvent(PaymentCompletedEvent event) {
        kafkaTemplate.send("payment.completed.topic", event);
    }

    public void publishPaymentRollbackEvent(PaymentRollbackEvent event) {
        kafkaTemplate.send("payment.rollback.topic", event);
    }
}
