package com.dev.backendspringboot.api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
    private String name;
    private int quantity;
    private String description;
    private String imageUrl;
}
