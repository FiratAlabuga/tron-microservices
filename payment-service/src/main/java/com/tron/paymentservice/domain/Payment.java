package com.tron.paymentservice.domain;

import com.tron.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment extends BaseEntity {
    private String paymentId;
    private String userId;
    private BigDecimal amount;
    private String currency;
    // ManyToOne ilişkisi - bir Payment sadece bir PaymentType'a sahiptir
    @ManyToOne
    @JoinColumn(name = "payment_type_id")  // foreign key kolonu
    private PaymentType paymentType;
}
