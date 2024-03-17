package com.dev.backendspringboot.api.dto.ref;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRef {
    private String id;
    private String name;
    private int quantity;
}
