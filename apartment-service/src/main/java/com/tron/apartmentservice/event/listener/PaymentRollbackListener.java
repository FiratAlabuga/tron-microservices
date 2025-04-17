package com.tron.apartmentservice.event.listener;


import com.tron.event.dto.PaymentRollbackEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentRollbackListener {
    private final Logger logger = LoggerFactory.getLogger(PaymentCompletedListener.class);

    @KafkaListener(topics = "payment-rollback-topic", groupId = "apartment-group")
    public void handlePaymentRollback(PaymentRollbackEvent event) {
        logger.warn("⛔ [PaymentRollbackListener] Ödeme geri alındı - SagaID: {}, Sebep: {}",
                event.getSagaId(), event.getReason());

        // Daire rezervasyonu geri alınabilir
        logger.info("🔄 Rollback işlemi yapılıyor - Apartment ID: {}, Tutar: {}", event.getApartmentId(), event.getAmount());
    }
}
