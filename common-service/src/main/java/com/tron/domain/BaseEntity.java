package com.tron.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Tüm entity'lerin türeteceği temel sınıf.
 * Ortak alanları ve audit bilgilerini içerir.
 *
 * @MappedSuperclass: Bu sınıfın bir entity olmadığını, diğer entity'ler tarafından extend edileceğini belirtir.
 * @EntityListeners: Entity'nin yaşam döngüsü event'lerini dinler (create, update gibi)
 * @CreatedBy ve @LastModifiedBy: Spring Security ile otomatik doldurulur
 * @Version: Optimistic locking için kullanılır
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "status")
    private Boolean status = true;

    @Version
    private Long version;
}