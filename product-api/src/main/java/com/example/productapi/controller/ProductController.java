package com.example.productapi.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.productapi.businessservices.ProductBusinessService;
import com.example.productapi.dto.ProductDTO;
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
import com.example.productapi.util.Mapper;

@RestController
@RequestMapping(ProductController.BASE_URL)
public class ProductController {

    /* 
        Product Controller is used for handling users HTTP requests and user can 
        find all the products, find product by id, search products by specific parameter,
        create product, delete product by id, delete all products, find all the categories,
        find category by id, delete category by id and delete all the categories or user
        can add the product (buy product)
    */

    public static final String BASE_URL = "/api/products";

    private final ProductBusinessService productBusinessService;
    private final Mapper mapper;

    @Autowired
    public ProductController(ProductBusinessService productBusinessService, Mapper mapper) {
        this.productBusinessService = productBusinessService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAllProducts() {
        List<Product> products = productBusinessService.findAllProducts();
        return new ResponseEntity<>(mapper.convertProductToProductDTO(products), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable String id)
            throws NotValidProductIdException, ProductNotFoundException {
        Product product = productBusinessService.findProductById(id);

        return new ResponseEntity<>(mapper.convertProductToProductDTO(product), HttpStatus.OK);
    }

    @GetMapping("/search/{param}")
    public ResponseEntity<List<ProductDTO>> search(@PathVariable String param)
            throws NotValidProductIdException, ProductNotFoundException {
        List<Product> foundProducts = productBusinessService.search(param);

        if (foundProducts.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.convertProductToProductDTO(foundProducts), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        if (productDTO.getCategory().getName() != null) {
            Product product = mapper.convertDtoToProduct(productDTO);
            Product saveProduct = productBusinessService.saveProduct(product);

            return new ResponseEntity<>(saveProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id)
            throws NotValidProductIdException, ProductNotFoundException {
        productBusinessService.deleteProductById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllProducts() {
        productBusinessService.deleteAllProducts();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> findAllCategories() {
        List<Category> categories = productBusinessService.findAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable String id) throws CategoryNotFoundException {
        Category category = productBusinessService.findCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable String id)
            throws NotValidProductIdException, ProductNotFoundException, CategoryNotFoundException {
        productBusinessService.deleteCategoryById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/categories")
    public ResponseEntity<Void> deleteAllCategories() {
        productBusinessService.deleteAllCategories();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addproduct")
    public ResponseEntity<Order> addProduct(@RequestHeader HashMap<String, String> params,
            @RequestBody List<OrderedProduct> orderedProducts) throws NotValidOrderedProductList,
            UserIdNotExistException, NotValidProductIdException, ProductNotFoundException, NotValidProductQuantity {
        String token = params.get("authorization");
        Order order = productBusinessService.addProduct(orderedProducts, token);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
