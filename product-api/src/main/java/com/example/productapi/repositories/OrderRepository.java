package com.example.productapi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.productapi.models.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    
}
