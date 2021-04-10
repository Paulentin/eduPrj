package com.home.zabara.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @NotNull
    @Id
    @EqualsAndHashCode.Exclude
    @GeneratedValue
    private UUID id;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    @EqualsAndHashCode.Exclude
    private Date createdAt;

    @Column(name = "last_updated_at", nullable = false)
    @LastModifiedDate
    @EqualsAndHashCode.Exclude
    private Date lastUpdatedAt;
}
