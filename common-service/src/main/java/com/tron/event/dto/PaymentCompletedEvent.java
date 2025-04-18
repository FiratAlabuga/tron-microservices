package com.tron.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentCompletedEvent {
    private String sagaId;
    private String apartmentId;
    private String residentId;
    private String paymentId;
    private BigDecimal amount;
    private String paymentTypeMethod;
    private String paymentStatus;
    private String note;
    private LocalDateTime paidAt;
}
