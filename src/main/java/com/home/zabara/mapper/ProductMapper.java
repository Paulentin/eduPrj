package com.home.zabara.mapper;

import com.home.zabara.dto.request.product.PatchProductRequest;
import com.home.zabara.dto.request.product.CreateProductRequest;
import com.home.zabara.dto.response.product.CreateProductResponse;
import com.home.zabara.dto.response.product.GetProductResponse;
import com.home.zabara.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;


@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ProductMapper {

    void update(PatchProductRequest patchProductRequest, @MappingTarget ProductEntity product);

    GetProductResponse asGetProductResponse(ProductEntity product);

    List<GetProductResponse> asGetProductResponseList(List<ProductEntity> products);

    ProductEntity asProductEntity(CreateProductRequest createProductRequest);

    CreateProductResponse asCreateProductResponse(ProductEntity product);

    PatchProductRequest asPatchProductRequest(ProductEntity product);
}
