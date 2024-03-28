package com.dev.backendspringboot.document;

import com.dev.backendspringboot.data.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.URI;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private int quantity;
    private double price;
    private String description;
    private Status status;
//TODO:    private URI imageDrive;
    @DBRef
    private List<ImageData> imageDataList;
    @DBRef
    private UserDocument user;
}
