package com.tron.paymentservice.dto;

import com.tron.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentDTO extends BaseDTO {
    private String paymentId;
    private String userId;
    private BigDecimal amount;
    private String currency;
    private PaymentTypeDTO paymentType; // PaymentTypeDTO ile ilişki
}
