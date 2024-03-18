package com.dev.backendspringboot.api.dto.response;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class OrderDetailDto {
    private String id;
    private int quantity;
    private double price;
}
