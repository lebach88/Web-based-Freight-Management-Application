package com.dev.backendspringboot.api.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductRequest {
    private String name;
    private int quantity;
    private double price;
    private String description;
// TODO:   private URI imageDrive;
    private List<String> imageDataList;
}
