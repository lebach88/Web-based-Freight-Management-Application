package com.dev.backendspringboot.api.dto.request;

import lombok.Data;

@Data
public class OrderDetailRequest {
    private int quantity;
    private double price;
}
