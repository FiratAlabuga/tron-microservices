package com.tron.event.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRollbackEvent {
    private String sagaId;
    private String apartmentId;
    private String reason;
}
