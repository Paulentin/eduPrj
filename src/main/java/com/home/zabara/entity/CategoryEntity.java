package com.home.zabara.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(
        name = "category",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})}
)
public class CategoryEntity extends BaseEntity {

    @NotBlank
    @Size(max = 64)
    @Column(name = "code", updatable = false)
    private String code;

    @NotBlank
    @Size(max = 64)
    @Column(name = "name")
    private String name;

    @NotBlank
    @Size(max = 64)
    @Column(name = "description")
    private String description;

    @NotBlank
    @Column(name = "sequence_number")
    private Integer sequenceNumber;

}
