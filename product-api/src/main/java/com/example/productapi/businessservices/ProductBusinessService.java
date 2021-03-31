package com.example.productapi.businessservices;

import java.util.List;

import com.example.productapi.exceptions.CategoryNotFoundException;
import com.example.productapi.exceptions.NotValidOrderedProductList;
import com.example.productapi.exceptions.NotValidProductIdException;
import com.example.productapi.exceptions.NotValidProductQuantity;
import com.example.productapi.exceptions.ProductNotFoundException;
import com.example.productapi.exceptions.UserIdNotExistException;
import com.example.productapi.models.Category;
import com.example.productapi.models.Order;
import com.example.productapi.models.OrderedProduct;
import com.example.productapi.models.Product;
import com.example.productapi.services.ProductServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductBusinessService {
    
    @Autowired
    private ProductServiceImpl productService;

    private final Logger logger = LoggerFactory.getLogger(ProductBusinessService.class);

    public List<Product> findAllProducts() {
        return productService.findAllProducts();
    }

    public Product findProductById(String id) throws NotValidProductIdException, ProductNotFoundException {
        logger.info("Finding product by id: {}", id);

        return productService.findProductById(id);
    }

    public Product findProductByName(String name) {
        logger.info("Finding product by name: {}", name);

        return productService.findProductByName(name);
    }

    public Product saveProduct(Product product) {
        logger.info("Saving product to the database: {}", product.toString());

        Product savedProduct = productService.saveProduct(product);

        logger.info("Product saved ... OK");

        return savedProduct;
    }

    public void deleteProductById(String id) throws NotValidProductIdException, ProductNotFoundException {
        logger.info("Deleting product with id: {}", id);

        productService.deleteProductById(id);

        logger.info("Product deleted ... OK");
    }

    public void deleteAllProducts() {
        logger.info("Deleting all the products from the DB");
        
        productService.deleteAllProducts();

        logger.info("Products deleteded ... OK");
    }

    public Order addProduct(List<OrderedProduct> orderedProducts, String token) throws NotValidOrderedProductList, UserIdNotExistException, NotValidProductIdException, ProductNotFoundException, NotValidProductQuantity {
        return productService.addProduct(orderedProducts, token);
    }

    public List<Category> findAllCategories() { 
        return productService.findAllCategories();
    }

    public Category findCategoryById(String id) throws CategoryNotFoundException {
        logger.info("Finding category by id: {}", id);

        return productService.findCategoryById(id);
    }

    public Category findCategoryByName(String name) {
        logger.info("Finding category by name: {}", name);

        return productService.findCategoryByName(name);
    }

    public Category saveCategory(Category category) {
        logger.info("Saving category to the database: {}", category.toString());

        Category savedCategory = productService.saveCategory(category);

        logger.info("Category saved ... OK");

        return savedCategory;
    }

    public void deleteCategoryById(String id) throws NotValidProductIdException, ProductNotFoundException, CategoryNotFoundException {
        logger.info("Deleting category by id: {}", id);

        productService.deleteCategoryById(id);
    }

    public void deleteAllCategories() {
        logger.info("Deleting all the categories from the DB");

        productService.deleteAllCategories();

        logger.info("Categories deleted ... OK");
    }

    public List<Product> search(String param) throws NotValidProductIdException, ProductNotFoundException {
        return productService.search(param);
    }

}
