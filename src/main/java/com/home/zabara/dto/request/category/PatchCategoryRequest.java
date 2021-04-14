package com.home.zabara.dto.request.category;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class PatchCategoryRequest {
    @NotNull
    private UUID id;

    private String code;

    private String name;

    private String description;

    private Integer sequenceNumber;
}
