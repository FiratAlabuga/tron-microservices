package com.tron.apartmentservice.domain;

import com.tron.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "residents")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Resident extends BaseEntity {
    private String residentId;
    private String apartmentId;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String block;
    private String number;
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;
}
