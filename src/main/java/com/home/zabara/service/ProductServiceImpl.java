package com.home.zabara.service;

import com.home.zabara.dto.request.product.CreateProductRequest;
import com.home.zabara.dto.response.product.CreateProductResponse;
import com.home.zabara.dto.response.product.GetProductResponse;
import com.home.zabara.entity.ProductEntity;
import com.home.zabara.mapper.ProductMapper;
import com.home.zabara.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    public GetProductResponse findBaseProductByPartNumberAndCatalogVersionId(String partNumber) {
        return productMapper.asGetProductResponse(productRepository.findByPartNumber(partNumber)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("product with partnumber %s not found", partNumber))));
    }

    @Override
    public CreateProductResponse createProduct(CreateProductRequest createProductRequest) {
        ProductEntity save = productRepository.save(productMapper.asProductEntity(createProductRequest));
        return productMapper.asCreateProductResponse(save);
    }

    @Override
    public List<GetProductResponse> findAll() {
        return productMapper.asGetProductResponseList(productRepository.findAll());
    }
}
