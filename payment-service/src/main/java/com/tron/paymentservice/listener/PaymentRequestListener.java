package com.tron.paymentservice.listener;

import com.tron.domain.enums.PaymentStatus;
import com.tron.event.dto.PaymentCompletedEvent;
import com.tron.event.dto.PaymentCreatedEvent;
import com.tron.event.dto.PaymentRollbackEvent;
import com.tron.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentRequestListener {
    private final Logger logger = LoggerFactory.getLogger(PaymentRequestListener.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final PaymentService paymentService;

    @KafkaListener(topics = "payment-topic", groupId = "payment-group")
    public void onPaymentCreated(PaymentCreatedEvent event) {
        logger.info("📩 [PaymentRequestListener] Ödeme isteği alındı - SagaID: {}, ApartmentID: {}, Tutar: {}",
                event.getSagaId(), event.getApartmentId(), event.getAmount());

        try {
            boolean success = paymentService.processPayment(event);

            if (success) {
                PaymentCompletedEvent completedEvent = new PaymentCompletedEvent();
                completedEvent.setSagaId(event.getSagaId());
                completedEvent.setApartmentId(event.getApartmentId());
                completedEvent.setStatus(PaymentStatus.SUCCESS);
                completedEvent.setMessage("Ödeme başarılı");

                kafkaTemplate.send("payment-completed-topic", completedEvent.getSagaId(), completedEvent);
                logger.info("✅ [PaymentService] Ödeme başarılı - Event gönderildi.");
            } else {
                sendRollbackEvent(event, "Banka işlemi reddetti.");
            }

        } catch (Exception e) {
            logger.error("❌ [PaymentService] Ödeme sırasında hata oluştu: {}", e.getMessage(), e);
            sendRollbackEvent(event, "Exception: " + e.getMessage());
        }
    }

    private void sendRollbackEvent(PaymentCreatedEvent event, String reason) {
        PaymentRollbackEvent rollbackEvent = new PaymentRollbackEvent();
        rollbackEvent.setSagaId(event.getSagaId());
        rollbackEvent.setApartmentId(event.getApartmentId());
        rollbackEvent.setAmount(event.getAmount());
        rollbackEvent.setReason(reason);

        kafkaTemplate.send("payment-rollback-topic", rollbackEvent.getSagaId(), rollbackEvent);
        logger.warn("⚠️ [PaymentService] Rollback event gönderildi - SagaID: {}, Reason: {}", event.getSagaId(), reason);
    }
}

