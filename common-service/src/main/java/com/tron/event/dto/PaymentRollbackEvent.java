package com.tron.event.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRollbackEvent {
    private String sagaId;
    private String apartmentId;
    private BigDecimal amount;
    private String reason;
}