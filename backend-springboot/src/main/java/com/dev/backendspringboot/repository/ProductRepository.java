package com.dev.backendspringboot.repository;

import com.dev.backendspringboot.document.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findProductsByUser(String userId);
}
