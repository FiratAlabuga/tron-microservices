package com.tron.apartmentservice.listener;

import com.tron.apartmentservice.domain.Resident;
import com.tron.apartmentservice.repository.ResidentRepository;
import com.tron.event.dto.PaymentCompletedEvent;
import com.tron.event.dto.PaymentRollbackEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApartmentListener {
    private final ResidentRepository residentRepository;

    @KafkaListener(
            topics = "payment.completed",
            containerFactory = "paymentCompletedListenerFactory"
    )
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        Resident resident = residentRepository.findByResidentId(event.getResidentId())
                .orElseThrow(() -> new RuntimeException("Resident not found"));

        resident.setBalance(resident.getBalance().subtract(event.getAmount()));
        residentRepository.save(resident);
    }

    @KafkaListener(
            topics = "payment.rollback",
            containerFactory = "paymentRollbackListenerFactory"
    )
    public void handlePaymentRollback(PaymentRollbackEvent event) {
        // Rollback işlemleri
    }
}