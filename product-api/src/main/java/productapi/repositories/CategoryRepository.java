package productapi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import productapi.models.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findByName(String name);
}
