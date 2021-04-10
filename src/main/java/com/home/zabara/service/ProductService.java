package com.home.zabara.service;


import com.home.zabara.dto.request.product.CreateProductRequest;
import com.home.zabara.dto.response.product.CreateProductResponse;
import com.home.zabara.dto.response.product.GetProductResponse;

import java.util.List;

public interface ProductService {
    GetProductResponse findBaseProductByPartNumberAndCatalogVersionId(String partNumber);
    CreateProductResponse createProduct(CreateProductRequest createProductRequest);
    List<GetProductResponse> findAll();
}
