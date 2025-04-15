package com.tron.apartmentservice.api.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ApartmentPaymentRequest {
    private String apartmentId;
    private String residentId;
    private BigDecimal amount;
    private String paymentMethodType;
}
