package com.home.zabara.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(
        name = "product",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"part_number"})}
)
public class ProductEntity extends BaseEntity {

    @NotBlank
    @Size(max = 64)
    @Column(name = "part_number", updatable = false)
    private String partNumber;

    @NotBlank
    @Size(max = 64)
    @Column(name = "short_desc")
    private String shortDesc;

    @NotBlank
    @Size(max = 64)
    @Column(name = "long_desc")
    private String longDesc;

    @NotNull
    @Column(name = "category_id")
    private UUID categoryID;
}
