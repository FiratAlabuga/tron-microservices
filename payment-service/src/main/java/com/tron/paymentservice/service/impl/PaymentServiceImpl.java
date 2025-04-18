package com.tron.paymentservice.service.impl;

import com.tron.Main;
import com.tron.paymentservice.dto.PaymentDTO;
import com.tron.paymentservice.mapper.PaymentMapper;
import com.tron.paymentservice.repository.PaymentRepository;
import com.tron.paymentservice.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);
    @Override
    public PaymentDTO getPaymentById(String paymentId) {
        // Fetch the payment from the repository
        var payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        // Convert to DTO and return
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);
        return paymentDTO;
    }

    @Override
    public PaymentDTO getPaymentByTransactionId(String transactionId) {
        // Fetch the payment from the repository
        var payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        // Convert to DTO and return
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);
        return paymentDTO;
    }

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        // Convert DTO to entity
        var payment = paymentMapper.toEntity(paymentDTO);
        // Validate payment ID
        if (payment.getPaymentId() == null) {
            throw new RuntimeException("Payment ID cannot be null");
        }
        // Check if payment already exists
        var existingPayment = paymentRepository.findByPaymentId(payment.getPaymentId());
        if (existingPayment.isPresent()) {
            throw new RuntimeException("Payment with this ID already exists");
        }
        // Save the payment
        paymentRepository.save(payment);
        // Convert to DTO and return
        PaymentDTO savedPaymentDTO = paymentMapper.toDto(payment);
        // Publish event (if needed)
        // apartmentEventPublisher.publishPaymentCreatedEvent(new PaymentCreatedEvent(payment.getPaymentId(), payment.getUserId(), payment.getAmount()));
        // Uncomment the above line to publish the event
        // Return the saved payment DTO
        return savedPaymentDTO;
    }

    @Override
    public PaymentDTO updatePayment(PaymentDTO paymentDTO, String paymentId) {
        // Fetch the existing payment
        var existingPayment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        // Update the payment details
        paymentMapper.updateEntity(paymentDTO, existingPayment);
        // Save the updated payment
        paymentRepository.save(existingPayment);
        // Convert to DTO and return
        PaymentDTO updatedPaymentDTO = paymentMapper.toDto(existingPayment);
        // Publish event (if needed)
        // apartmentEventPublisher.publishPaymentUpdatedEvent(new PaymentUpdatedEvent(existingPayment.getPaymentId(), existingPayment.getUserId(), existingPayment.getAmount()));
        // Uncomment the above line to publish the event
        // Return the updated payment DTO
        return updatedPaymentDTO;
    }

    @Override
    public void deletePayment(String paymentId) {
        // Fetch the payment
        var payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        // Mark as deleted
        payment.setStatus(false);
        // Save the updated payment
        paymentRepository.save(payment);
        // Publish event (if needed)
        // apartmentEventPublisher.publishPaymentDeletedEvent(new PaymentDeletedEvent(payment.getPaymentId()));
        // Uncomment the above line to publish the event

    }

    @Override
    public Boolean processPayment(String paymentTypeId, BigDecimal amount) {
        // Implement the logic to process the payment
        // This could involve calling an external payment gateway or service
        // For now, we'll just simulate a successful payment processing
        // Generate a transaction ID (for demonstration purposes)
        String transactionId = "TXN-" + System.currentTimeMillis();

        return true;
    }

    @Override
    public String refundPayment(String transactionId) {
        return "";
    }
}
