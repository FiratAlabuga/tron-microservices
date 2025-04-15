package com.tron.event;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
/**
 * Represents a request for a payment event.
 */
public class PaymentEventRequest {
    private String residentId;
    private String apartmentId;
    private BigDecimal amount;
    private String paymentMethod;

    // Getters and Setters
}
