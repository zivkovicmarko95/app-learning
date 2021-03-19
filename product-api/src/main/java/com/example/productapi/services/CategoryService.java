package com.example.productapi.services;

import java.util.List;

import com.example.productapi.exceptions.CategoryNotFoundException;
import com.example.productapi.exceptions.NotValidProductIdException;
import com.example.productapi.exceptions.ProductNotFoundException;
import com.example.productapi.models.Category;

public interface CategoryService {
    
    List<Category> findAllCategories();
    
    Category findCategoryById(String id) throws CategoryNotFoundException;

    Category findCategoryByName(String name);

    Category saveCategory(Category category);

    void deleteCategoryById(String id) throws NotValidProductIdException, ProductNotFoundException, CategoryNotFoundException;

    void deleteAllCategories();

}
