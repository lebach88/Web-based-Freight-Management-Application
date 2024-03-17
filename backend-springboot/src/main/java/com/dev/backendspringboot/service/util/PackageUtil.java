package com.dev.backendspringboot.service.util;

import com.dev.backendspringboot.api.dto.ref.ProductRef;
import com.dev.backendspringboot.api.dto.request.PackageRequest;
import com.dev.backendspringboot.api.dto.response.PackageDto;
import com.dev.backendspringboot.document.Package;
import com.dev.backendspringboot.document.Product;
import com.dev.backendspringboot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PackageUtil {
    private final ProductRepository productRepository;
    public PackageDto mapPackageToPackageDto(Package packageDocument){
        List<ProductRef> productRefs = null;
        if (packageDocument.getProducts() != null){
            productRefs = new ArrayList<>();
            for (Product item : packageDocument.getProducts()) {
                ProductRef build = ProductRef.builder().id(item.getId()).name(item.getName()).build();
                productRefs.add(build);
            }
        }
        return PackageDto.builder()
                .id(packageDocument.getId())
                .name(packageDocument.getName())
                .type(packageDocument.getType())
                .createdAt(packageDocument.getCreatedAt())
                .updatedOn(packageDocument.getUpdatedOn())
                .products(productRefs)
                .build();
    }
    public List<Product> extraProducts(PackageRequest packageRequest) {
        List<Product> products = packageRequest.getProducts()
                .stream().map(id -> productRepository.findById(id).orElseThrow())
                .collect(Collectors.toList());
        return products;
    }
}
