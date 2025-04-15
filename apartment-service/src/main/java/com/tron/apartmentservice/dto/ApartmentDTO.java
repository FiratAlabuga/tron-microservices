package com.tron.apartmentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApartmentDTO {
    private String apartmentId;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String block;
    private String number;
    private int totalUnits;
    private int availableUnits;
}
