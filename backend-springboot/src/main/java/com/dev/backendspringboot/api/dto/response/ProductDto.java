package com.dev.backendspringboot.api.dto.response;

import com.dev.backendspringboot.api.dto.ref.UserRef;
import com.dev.backendspringboot.data.Status;
import com.dev.backendspringboot.document.ImageData;
import lombok.Builder;
import lombok.Data;

import java.net.URI;
import java.util.List;

@Data
@Builder
public class ProductDto {
    private String id;
    private String name;
    private int quantity;
    private String description;
    private Status status;
//TODO:    private URI image;
    private List<ImageData> imageDataList;
    private UserRef user;
}
