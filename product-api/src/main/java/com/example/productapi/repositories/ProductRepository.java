package com.example.productapi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.productapi.models.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByName(String name);
}
