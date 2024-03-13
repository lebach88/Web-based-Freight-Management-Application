package com.dev.backendspringboot.service.Impl;

import com.dev.backendspringboot.api.dto.request.ProductRequest;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.ProductDto;
import com.dev.backendspringboot.api.error.ProductNotFoundException;
import com.dev.backendspringboot.api.error.ServerErrorException;
import com.dev.backendspringboot.document.Product;
import com.dev.backendspringboot.document.UserDocument;
import com.dev.backendspringboot.repository.ProductRepository;
import com.dev.backendspringboot.service.ProductService;
import com.dev.backendspringboot.service.util.ProductUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductUtil productUtil;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductUtil productUtil) {
        this.productRepository = productRepository;
        this.productUtil = productUtil;
    }

    @Override
    public List<ProductDto> findAllProduct() {
        try {
            List<Product> products = productRepository.findAll();
            return products.stream().map(product -> productUtil.mapProductToProductDto(product)).collect(Collectors.toList());
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi tìm tất cả sản phẩm", ex);
        }
    }

    @Override
    public ProductDto createNewProduct(ProductRequest productRequest, HttpServletRequest httpServletRequest) {
        try {
            UserDocument userCreate = productUtil.getUserFromToken(httpServletRequest);
            Product product = productUtil.mapProductRequestToProduct(productRequest);
            product.setUser(userCreate);
            Product saveProduct = productRepository.insert(product);
            return productUtil.mapProductToProductDto(saveProduct);
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi tạo sản phẩm ", ex);
        }
    }

    @Override
    public MessageApi deleteAllProduct() {
       try {
           productRepository.deleteAll();
           return productUtil.getMessageDelete("deleted all successfully");
       }catch (ServerErrorException ex){
           throw new ServerErrorException("Đã xảy ra lỗi khi xóa tất cả sản phẩm ", ex);
       }
    }

    @Override
    public ProductDto getOneProductById(String productId) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(()-> new ProductNotFoundException("Không tìm thấy sản phẩm với id:"+productId));
            return productUtil.mapProductToProductDto(product);
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi tìm một sản phẩm với id: "+ productId, ex);
        }
    }

    @Override
    public ProductDto updateOneProduct(String productId, ProductRequest productRequest) {
        try {
            Product product = productUtil.mapProductDtoToProduct(getOneProductById(productId));
            product.setName(productRequest.getName());
            product.setImageUrl(productRequest.getImageUrl());
            product.setDescription(productRequest.getDescription());
            product.setQuantity(productRequest.getQuantity());
            productRepository.save(product);
            return productUtil.mapProductToProductDto(product);
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi cập nhật sản phẩm ", ex);
        }
    }

    @Override
    public MessageApi deleteOneProduct(String productId) {
        try {
            if (productRepository.existsById(productId)){
                productRepository.deleteById(productId);
                return productUtil.getMessageDelete("deleted successfully");
            }else {
                return productUtil.getMessageDelete("not exist id:"+productId);
            }
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi xóa một sản phẩm ", ex);
        }
    }
}
