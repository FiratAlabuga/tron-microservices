package com.tron.apartmentservice.domain;

import com.tron.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "apartments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Apartment extends BaseEntity {
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

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Resident> residents = new ArrayList<>();
}
