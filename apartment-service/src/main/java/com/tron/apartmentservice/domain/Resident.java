package com.tron.apartmentservice.domain;

import com.tron.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "residents")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Resident extends BaseEntity {
}
