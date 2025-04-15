package com.tron.apartmentservice.event.consumer;

import com.tron.event.dto.PaymentRollbackEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentRollbackConsumer {

    @KafkaListener(topics = "payment-rollback-topic", groupId = "apartment-service")
    public void consumeRollback(PaymentRollbackEvent rollbackEvent) {
        log.warn("Rollback requested for SAGA ID {}: Reason: {}", rollbackEvent.getSagaId(), rollbackEvent.getReason());
        // TODO: Undo apartment record (delete, flag, etc.)
    }
}