package com.home.zabara.service;

import com.home.zabara.dto.request.category.CreateCategoryRequest;
import com.home.zabara.dto.response.category.CreateCategoryResponse;
import com.home.zabara.dto.response.category.GetCategoryResponse;

import java.util.List;

public interface CategoryService {
    List<GetCategoryResponse> findAll();

    CreateCategoryResponse createCategory(CreateCategoryRequest createCategoryRequest);
}
