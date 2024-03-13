package com.dev.backendspringboot.api.dto.response;

import com.dev.backendspringboot.api.dto.ref.UserRef;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private String id;
    private String name;
    private int quantity;
    private String description;
    private String imageUrl;
    private UserRef user;
}
