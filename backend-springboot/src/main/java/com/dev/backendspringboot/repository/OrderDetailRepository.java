package com.dev.backendspringboot.repository;

import com.dev.backendspringboot.document.OrderDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends MongoRepository<OrderDetail,String> {
}
