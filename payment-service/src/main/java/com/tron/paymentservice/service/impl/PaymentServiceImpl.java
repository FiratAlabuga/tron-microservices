package com.tron.paymentservice.service.impl;

import com.tron.paymentservice.dto.PaymentDTO;
import com.tron.paymentservice.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    @Override
    public PaymentDTO getPaymentById(String paymentId) {
        return null;
    }

    @Override
    public PaymentDTO getPaymentByTransactionId(String transactionId) {
        return null;
    }

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        return null;
    }

    @Override
    public PaymentDTO updatePayment(PaymentDTO paymentDTO, String paymentId) {
        return null;
    }

    @Override
    public void deletePayment(String paymentId) {

    }

    @Override
    public String processPayment(String paymentTypeId, BigDecimal amount) {
        return "";
    }

    @Override
    public String refundPayment(String transactionId) {
        return "";
    }
}
