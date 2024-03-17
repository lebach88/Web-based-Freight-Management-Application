package com.dev.backendspringboot.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "packages")
public class Package {
    @Id
    private String id;
    private String name;
    private String type;
    @JsonFormat(pattern="dd-MM-yyy HH:mm:ss")
    @CreatedDate
    private LocalDateTime createdAt;
    @JsonFormat(pattern="dd-MM-yyy HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime updatedOn;
    @DBRef
    private List<Product> products;
}
