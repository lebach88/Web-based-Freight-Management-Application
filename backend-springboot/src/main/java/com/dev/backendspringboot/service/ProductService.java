package com.dev.backendspringboot.service;

import com.dev.backendspringboot.api.dto.request.ProductRequest;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.ProductDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAllProduct();

    ProductDto createNewProduct(ProductRequest productRequest, HttpServletRequest httpServletRequest);

    MessageApi deleteAllProduct();

    ProductDto getOneProductById(String productId);

    ProductDto updateOneProduct(String productId, ProductRequest productRequest);

    MessageApi deleteOneProduct(String productId);

    List<ProductDto> findAllProductByUserId(String userId);
}
