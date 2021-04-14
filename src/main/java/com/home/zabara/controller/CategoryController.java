package com.home.zabara.controller;

import com.home.zabara.dto.request.category.CreateCategoryRequest;
import com.home.zabara.dto.request.product.CreateProductRequest;
import com.home.zabara.dto.response.category.CreateCategoryResponse;
import com.home.zabara.dto.response.category.GetCategoryResponse;
import com.home.zabara.dto.response.product.CreateProductResponse;
import com.home.zabara.service.CategoryService;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<GetCategoryResponse>> getAllCategories() {
        List<GetCategoryResponse> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateCategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest, UriComponentsBuilder uriComponentsBuilder) {
        CreateCategoryResponse category = categoryService.createCategory(createCategoryRequest);
        UriComponents uriComponents = uriComponentsBuilder.path("category/{id}").buildAndExpand(category.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(category);
    }

}
