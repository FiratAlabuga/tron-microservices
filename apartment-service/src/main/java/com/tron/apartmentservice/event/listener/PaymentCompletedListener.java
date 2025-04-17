package com.tron.apartmentservice.event.listener;

import com.tron.domain.enums.PaymentStatus;
import com.tron.event.dto.PaymentCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentCompletedListener {
    private final Logger logger = LoggerFactory.getLogger(PaymentCompletedListener.class);
    @KafkaListener(topics = "payment-completed-topic", groupId = "apartment-group")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        logger.info("✅ [PaymentCompletedListener] Ödeme tamamlandı - SagaID: {}, Durum: {}, Mesaj: {}",
                event.getSagaId(), event.getStatus(), event.getMessage());

        if (event.getStatus() == PaymentStatus.SUCCESS) {
            // Kiracı yerleştirme işlemleri yapılabilir
            logger.info("🏠 Kiracı yerleştiriliyor - Apartment ID: {}", event.getApartmentId());
        }
    }
}
