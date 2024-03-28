package com.dev.backendspringboot.service.util;

import com.dev.backendspringboot.api.dto.ref.UserRef;
import com.dev.backendspringboot.api.dto.request.ProductRequest;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.ProductDto;
import com.dev.backendspringboot.document.ImageData;
import com.dev.backendspringboot.document.Product;
import com.dev.backendspringboot.document.UserDocument;
import com.dev.backendspringboot.jwt.JwtService;
import com.dev.backendspringboot.repository.ImageDataRepository;
import com.dev.backendspringboot.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductUtil {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ImageDataRepository imageDataRepository;
    public ProductDto mapProductToProductDto(Product product){
        UserRef userRef = UserRef.builder().id(product.getUser().getId()).email(product.getUser().getEmail()).build();
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .imageDataList(product.getImageDataList())
                .status(product.getStatus())
                .quantity(product.getQuantity())
                .user(userRef)
                .build();
    }
    public UserDocument getUserFromToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String email = jwtService.extraUsername(token);
        UserDocument userCreate =userRepository.findByEmail(email).orElseThrow();
        return userCreate;
    }
    public Product mapProductRequestToProduct(ProductRequest productRequest) {

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .imageDataList(convertImageData(productRequest))
                .quantity(productRequest.getQuantity())
                .build();
        return product;
    }
    public List<ImageData> convertImageData(ProductRequest productRequest){
        List<ImageData> imageDataList = new ArrayList<>();
        for (String imageName : productRequest.getImageDataList()){
            imageDataList.add(imageDataRepository.findByName(imageName).orElse(null));
        }
        return imageDataList;
    }
    public Product mapProductDtoToProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .imageDataList(productDto.getImageDataList())
                .quantity(productDto.getQuantity())
                .build();
        return product;
    }
    public MessageApi getMessageDelete(String message) {
        return MessageApi.builder().message(message).timestamp(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))).build();
    }
}
