package com.dev.backendspringboot.repository;

import com.dev.backendspringboot.document.ImageData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageDataRepository extends MongoRepository<ImageData, String> {
    Optional<ImageData> findByName(String name);

    void deleteById(String imageId);
}
