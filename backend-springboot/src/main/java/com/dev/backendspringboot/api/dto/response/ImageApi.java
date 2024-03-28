package com.dev.backendspringboot.api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.net.URI;
import java.util.List;
@Builder
@Data
public class ImageApi {
    private int status;
    private String message;
    private URI image;
}
