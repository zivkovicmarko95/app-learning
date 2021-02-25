package productapi.services;

import java.util.List;

import productapi.exceptions.CategoryNotFoundException;
import productapi.exceptions.NotValidProductIdException;
import productapi.exceptions.ProductNotFoundException;
import productapi.models.Category;

public interface CategoryService {
    
    List<Category> findAllCategories();
    
    Category findCategoryById(String id) throws CategoryNotFoundException;

    Category findCategoryByName(String name);

    Category saveCategory(Category category);

    void deleteCategoryById(String id) throws NotValidProductIdException, ProductNotFoundException, CategoryNotFoundException;

    void deleteAllCategories();

}
