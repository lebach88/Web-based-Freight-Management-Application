package com.dev.backendspringboot.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDataDto {
    private String id;
    private String name;
    private String type;
    private byte[] imageData;
}
