package com.home.zabara.service;

import com.home.zabara.dto.request.category.CreateCategoryRequest;
import com.home.zabara.dto.response.category.CreateCategoryResponse;
import com.home.zabara.dto.response.category.GetCategoryResponse;
import com.home.zabara.entity.CategoryEntity;
import com.home.zabara.mapper.CategoryMapper;
import com.home.zabara.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<GetCategoryResponse> findAll() {
        return categoryMapper.asGetCategoryResponseList(categoryRepository.findAll());
    }

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) {
        CategoryEntity save = categoryRepository.save(categoryMapper.asCategoryEntity(createCategoryRequest));
        return categoryMapper.asCreateCategoryResponse(save);
    }
}
