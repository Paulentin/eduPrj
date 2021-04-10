package com.home.zabara.dto.request.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
public class CreateProductRequest {

    @NotBlank
    private String partNumber;

    @NotBlank
    private String shortDesc;

    @NotBlank
    private String longDesc;

    @NotNull
    private UUID categoryID;
}