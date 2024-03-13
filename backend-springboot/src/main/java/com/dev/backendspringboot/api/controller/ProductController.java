package com.dev.backendspringboot.api.controller;

import com.dev.backendspringboot.api.dto.request.ProductRequest;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.ProductDto;
import com.dev.backendspringboot.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> findAllProduct(){
        return ResponseEntity.ok(productService.findAllProduct());
    }
    @PostMapping("/")
    public ResponseEntity<ProductDto> createNewProduct(@RequestBody ProductRequest productRequest, HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(productService.createNewProduct(productRequest,httpServletRequest));
    }
    @DeleteMapping("/")
    public ResponseEntity<MessageApi> deleteAllProduct(){
        return ResponseEntity.ok(productService.deleteAllProduct());
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getOneProductById(@PathVariable String productId){
        return ResponseEntity.ok(productService.getOneProductById(productId));
    }
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateOneProduct(@PathVariable String productId, @RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productService.updateOneProduct(productId,productRequest));
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<MessageApi> deleteOneProductById(@PathVariable String productId){
        return ResponseEntity.ok(productService.deleteOneProduct(productId));
    }
}
