package com.tron.paymentservice.repository;

import com.tron.paymentservice.domain.Payment;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentId(String paymentId);
    Optional<Payment> findByTransactionId(String transactionId);
    Payment findByUserId(String userId);
    Payment findByPaymentTypeId(String paymentTypeId);
    Payment findByStatus(String status);
}
