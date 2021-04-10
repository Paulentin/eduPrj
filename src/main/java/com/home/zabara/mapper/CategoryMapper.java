package com.home.zabara.mapper;

import com.home.zabara.dto.request.category.PatchCategoryRequest;
import com.home.zabara.dto.request.category.CreateCategoryRequest;
import com.home.zabara.dto.response.category.CreateCategoryResponse;
import com.home.zabara.dto.response.category.GetCategoryResponse;
import com.home.zabara.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;


@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CategoryMapper {
    
    void update(PatchCategoryRequest patchCategoryRequest, @MappingTarget CategoryEntity category);

    GetCategoryResponse asGetCategoryResponse(CategoryEntity category);

    List<GetCategoryResponse> asGetCategoryResponseList(List<CategoryEntity> categorys);

    CategoryEntity asCategoryEntity(CreateCategoryRequest createCategoryRequest);
    
    CreateCategoryResponse asCreateCategoryResponse(CategoryEntity category);

    PatchCategoryRequest asPatchCategoryRequest(CategoryEntity category);
}
