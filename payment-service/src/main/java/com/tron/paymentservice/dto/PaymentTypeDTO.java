package com.tron.paymentservice.dto;

import com.tron.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentTypeDTO extends BaseDTO {
    private String paymentTypeId;
    private String type;
    private String description;
    private String icon;
    private String provider;
    private String providerUrl;
    private String providerApiKey;
    private String providerApiSecret;
}
