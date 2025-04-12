package com.tron.paymentservice.repository;

import com.tron.paymentservice.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByPaymentId(String paymentId);
    Payment findByTransactionId(String transactionId);
    Payment findByUserId(String userId);
    Payment findByPaymentTypeId(String paymentTypeId);
    Payment findByStatus(String status);
}
