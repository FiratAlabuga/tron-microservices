package com.tron.event.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a payment created event.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreatedEvent {
    private String sagaId;
    private String apartmentId;
    private String residentId;
    private BigDecimal amount;
    private String paymentTypeMethod;
    private LocalDateTime paidAt;
}
