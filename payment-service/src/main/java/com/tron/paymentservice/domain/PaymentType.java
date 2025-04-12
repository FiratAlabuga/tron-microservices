package com.tron.paymentservice.domain;

import com.tron.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * PaymentType entity class that represents different types of payment methods.
 * It extends BaseEntity to inherit common fields.
 */

@Entity
@Table(name = "payment_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentType extends BaseEntity {
    private String paymentTypeId;
    private String type;
    private String description;
    private String icon;
    private String provider;
    private String providerUrl;
    private String providerApiKey;
    private String providerApiSecret;
    // OneToMany ilişkisi - bir PaymentType birçok Payment'a sahip olabilir
    @OneToMany(mappedBy = "paymentType")
    private List<Payment> payments;
}
