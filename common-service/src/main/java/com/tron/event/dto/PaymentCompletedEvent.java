package com.tron.event.dto;

import com.tron.domain.enums.PaymentStatus;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCompletedEvent {
    private String sagaId;
    private String apartmentId;
    private PaymentStatus status;
    private String message;
}