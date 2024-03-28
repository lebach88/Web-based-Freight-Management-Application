package com.dev.backendspringboot.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "imageData")
public class ImageData {
    @Id
    private String id;
    private String name;
    private String type;
    private byte[] imageData;
}
