package com.tron.apartmentservice.dto;

import com.tron.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ResidentDTO extends BaseDTO {
    private String residentId;
    private String apartmentId;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String block;
    private String number;
    private BigDecimal balance;
}
