package com.dev.backendspringboot.api.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PackageRequest {
    private String name;
    private String type;
    private List<String> products;
}
