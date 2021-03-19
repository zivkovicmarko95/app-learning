package com.example.productapi.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.productapi.constants.ProductsConstants;
import com.example.productapi.models.Category;
import com.example.productapi.models.Product;
import com.example.productapi.repositories.CategoryRepository;
import com.example.productapi.repositories.ProductRepository;

@Component
public class Bootstrap implements CommandLineRunner {

    /*
        Bootstrap class is used only for importing dummy data in the database
    */
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public Bootstrap(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createData();
    }

    private void createData() {

        if (productRepository.count() > 0) {
            logger.info(ProductsConstants.DELETED_PRODUCTS_COLLECTION);
            productRepository.deleteAll();
        }

        if (categoryRepository.count() > 0) {
            logger.info(ProductsConstants.DELETED_CATEGORY_COLLECTION);
            categoryRepository.deleteAll();
        }

        Product product1 = new Product("title1", "name1", "description1", 12.22, 10);
        Product product2 = new Product("title2", "name2", "description2", 10.11, 15);
        Product product3 = new Product("title3", "name3", "description3", 16.81, 25);
        Product product4 = new Product("title4", "name3", "description4", 14.31, 21);
        Product product5 = new Product("title5", "name3", "description5", 15.15, 30);

        Category category1 = new Category("category-name1", "category-description1");
        Category category2 = new Category("category-name2", "category-description2");

        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);
        Product savedProduct3 = productRepository.save(product3);
        Product savedProduct4 = productRepository.save(product4);
        Product savedProduct5 = productRepository.save(product5);

        Category savedCategory1 = categoryRepository.save(category1);
        Category savedCategory2 = categoryRepository.save(category2);

        List<String> productIdsForCategory1 = new ArrayList<>();
        productIdsForCategory1.add(savedProduct1.getId());
        productIdsForCategory1.add(savedProduct2.getId());
        productIdsForCategory1.add(savedProduct3.getId());

        savedProduct1.setCategory(savedCategory1);
        savedProduct2.setCategory(savedCategory1);
        savedProduct3.setCategory(savedCategory1);
        savedProduct4.setCategory(savedCategory2);
        savedProduct5.setCategory(savedCategory2);

        savedCategory1.setProductIds(productIdsForCategory1);
        categoryRepository.save(savedCategory1);
        
        productRepository.save(savedProduct1);
        productRepository.save(savedProduct2);
        productRepository.save(savedProduct3);

        List<String> productIdsForCategory2 = new ArrayList<>();
        productIdsForCategory2.add(savedProduct4.getId());
        productIdsForCategory2.add(savedProduct5.getId());

        savedCategory2.setProductIds(productIdsForCategory2);
        categoryRepository.save(savedCategory2);

        productRepository.save(savedProduct4);
        productRepository.save(savedProduct5);

        logger.info(ProductsConstants.COLLECTIONS_PREPARED);

    }
    
}
