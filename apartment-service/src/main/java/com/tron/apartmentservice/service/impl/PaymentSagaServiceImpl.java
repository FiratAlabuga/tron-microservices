package com.tron.apartmentservice.service.impl;

import com.tron.apartmentservice.api.request.ApartmentPaymentRequest;
import com.tron.apartmentservice.service.PaymentSagaService;
import com.tron.domain.enums.PaymentStatus;
import com.tron.event.dto.PaymentCreatedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentSagaServiceImpl implements PaymentSagaService {
    private final KafkaTemplate<String,Object> kafkaTemplate;
    @Override
    public void startPaymentSaga(ApartmentPaymentRequest apartmentPaymentRequest) {
        PaymentCreatedEvent event = new PaymentCreatedEvent();
        event.setSagaId(UUID.randomUUID().toString());
        event.setApartmentId(apartmentPaymentRequest.getApartmentId());
        event.setResidentId(apartmentPaymentRequest.getResidentId());
        event.setAmount(apartmentPaymentRequest.getAmount());
        event.setPaymentTypeMethod(apartmentPaymentRequest.getPaymentMethodType());
        event.setPaidAt(LocalDateTime.now());

        kafkaTemplate.send("payment-topic", event.getSagaId(), event);
    }
}
