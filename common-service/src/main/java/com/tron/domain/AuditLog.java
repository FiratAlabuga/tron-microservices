package com.tron.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuditLog extends BaseEntity {
    private String sagaId;
    private String apartmentId;
    private String message;
    private String errorReason;
    private LocalDateTime timestamp;
}
