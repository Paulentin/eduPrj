package com.home.zabara.controller;

import com.home.zabara.dto.request.product.CreateProductRequest;
import com.home.zabara.dto.response.product.CreateProductResponse;
import com.home.zabara.dto.response.product.GetProductResponse;
import com.home.zabara.service.ProductService;
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
@RequestMapping("/product")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<GetProductResponse>> getAllProducts() {
        List<GetProductResponse> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateProductResponse> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest, UriComponentsBuilder uriComponentsBuilder) {
        CreateProductResponse product = productService.createProduct(createProductRequest);
        UriComponents uriComponents = uriComponentsBuilder.path("product/{id}").buildAndExpand(product.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(product);
    }

}
