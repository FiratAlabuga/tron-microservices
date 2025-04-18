package com.tron.paymentservice.event.listener;

import com.tron.domain.enums.PaymentMethodType;
import com.tron.domain.enums.PaymentStatus;
import com.tron.event.dto.PaymentCompletedEvent;
import com.tron.event.dto.PaymentCreatedEvent;
import com.tron.event.dto.PaymentRollbackEvent;
import com.tron.paymentservice.event.publisher.PaymentEventPublisher;
import com.tron.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentListener {
    private static final Logger logger = LoggerFactory.getLogger(PaymentListener.class);
    private final PaymentService paymentService;
    private final PaymentEventPublisher paymentEventPublisher;

    @Value("${kafka.topics.payment-created}")
    private String topic;

    @KafkaListener(
            topics = "kafka.topics.payment-created",
            groupId = "payment-service-group",
            containerFactory = "paymentCreatedListenerFactory"
    )
    public void handlePaymentCreatedEvent(PaymentCreatedEvent event) {
        logger.info("Payment CREATED event received: {}", event);
        // İş mantığı: Ödeme işlemini başlat, başarılıysa completed event, aksi halde rollback event publish et
        try {
            // Ödeme işlemini başlat
            Boolean processedPayment = paymentService.processPayment(event.getSagaId(), event.getAmount());
            if (processedPayment) {
                // Ödeme başarılıysa
                // Ödeme işlemini tamamla
                PaymentCompletedEvent paymentCompletedEvent = getPaymentCompletedEvent(event);
                paymentEventPublisher.publishPaymentCompletedEvent(paymentCompletedEvent);
                logger.info("Payment processed successfully for sagaId: {}", paymentCompletedEvent.getSagaId());
            } else {
                // Ödeme başarısızsa
                // Ödeme işlemini geri al
                PaymentRollbackEvent paymentRollbackEvent = getPaymentRollbackEvent(event);
                paymentEventPublisher.publishPaymentRollbackEvent(paymentRollbackEvent);
                logger.error("Payment processing failed for sagaId: {}", paymentRollbackEvent.getSagaId());
            }
        } catch (Exception e) {
            PaymentRollbackEvent paymentRollbackEvent = getPaymentRollbackEvent(event);
            logger.error("Error processing payment created event: {}", e.getMessage());
            paymentEventPublisher.publishPaymentRollbackEvent(paymentRollbackEvent);
        }
    }

    private static PaymentRollbackEvent getPaymentRollbackEvent(PaymentCreatedEvent event) {
        PaymentRollbackEvent paymentRollbackEvent = new PaymentRollbackEvent();
        paymentRollbackEvent.setSagaId(UUID.randomUUID().toString());
        paymentRollbackEvent.setApartmentId(event.getApartmentId());
        paymentRollbackEvent.setResidentId(event.getResidentId());
        paymentRollbackEvent.setPaymentTypeMethod(event.getPaymentTypeMethod());
        paymentRollbackEvent.setRollbackAt(event.getPaidAt());
        paymentRollbackEvent.setPaymentStatus(PaymentStatus.ROLLBACK.getStatus());
        return paymentRollbackEvent;
    }

    private static PaymentCompletedEvent getPaymentCompletedEvent(PaymentCreatedEvent event) {
        PaymentCompletedEvent paymentCompletedEvent = new PaymentCompletedEvent();
        paymentCompletedEvent.setSagaId(UUID.randomUUID().toString());
        paymentCompletedEvent.setApartmentId(event.getApartmentId());
        paymentCompletedEvent.setResidentId(event.getResidentId());
        paymentCompletedEvent.setAmount(event.getAmount());
        paymentCompletedEvent.setPaymentTypeMethod(event.getPaymentTypeMethod());
        paymentCompletedEvent.setPaidAt(event.getPaidAt());
        paymentCompletedEvent.setPaymentStatus(PaymentStatus.COMPLETED.getStatus());
        paymentCompletedEvent.setPaymentTypeMethod(PaymentMethodType.CASH.getValue());
        return paymentCompletedEvent;
    }
}
