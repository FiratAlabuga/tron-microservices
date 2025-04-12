package com.tron.paymentservice.service;

import com.tron.paymentservice.dto.PaymentDTO;

import java.math.BigDecimal;

public interface PaymentService {
    PaymentDTO getPaymentById(String paymentId);
    PaymentDTO getPaymentByTransactionId(String transactionId);
    PaymentDTO createPayment(PaymentDTO paymentDTO);
    PaymentDTO updatePayment(PaymentDTO paymentDTO, String paymentId);
    void deletePayment(String paymentId);
    /**
     * Process a payment with the given payment type and amount.
     *
     * @param paymentTypeId the ID of the payment type
     * @param amount        the amount to be paid
     * @return a confirmation message or transaction ID
     */
    String processPayment(String paymentTypeId, BigDecimal amount);

    /**
     * Refund a payment with the given transaction ID.
     *
     * @param transactionId the ID of the transaction to be refunded
     * @return a confirmation message or refund ID
     */
    String refundPayment(String transactionId);
}
