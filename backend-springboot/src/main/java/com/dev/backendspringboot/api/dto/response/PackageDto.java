package com.dev.backendspringboot.api.dto.response;

import com.dev.backendspringboot.api.dto.ref.ProductRef;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
public class PackageDto {
    private String id;
    private String name;
    private String type;
    @JsonFormat(pattern="dd-MM-yyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern="dd-MM-yyy HH:mm:ss")
    private LocalDateTime updatedOn;
    private List<ProductRef> products;
}
