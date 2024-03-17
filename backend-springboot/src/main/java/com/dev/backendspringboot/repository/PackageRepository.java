package com.dev.backendspringboot.repository;

import com.dev.backendspringboot.document.Package;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends MongoRepository<Package,String> {
}
