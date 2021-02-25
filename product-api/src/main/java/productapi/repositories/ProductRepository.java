package productapi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import productapi.models.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByName(String name);
}
