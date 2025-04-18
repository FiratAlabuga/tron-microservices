package com.tron.event.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRollbackEvent {
    private String sagaId;
    private String apartmentId;
    private String residentId;
    private String paymentId;
    private String paymentTypeMethod;
    private String paymentStatus;
    private String reason;
    private String errorCode;
    private LocalDateTime rollbackAt;
}
