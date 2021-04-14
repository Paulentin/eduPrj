package com.home.zabara.dto.response.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class GetCategoryResponse {

    @NotNull
    private UUID id;

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    private String description;

    private Integer sequenceNumber;
}
