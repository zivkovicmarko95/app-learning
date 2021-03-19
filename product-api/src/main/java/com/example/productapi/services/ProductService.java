package com.example.productapi.services;

import java.util.List;

import com.example.productapi.exceptions.NotValidProductIdException;
import com.example.productapi.exceptions.NotValidProductQuantity;
import com.example.productapi.exceptions.NotValidOrderedProductList;
import com.example.productapi.exceptions.ProductNotFoundException;
import com.example.productapi.exceptions.UserIdNotExistException;
import com.example.productapi.models.Order;
import com.example.productapi.models.OrderedProduct;
import com.example.productapi.models.Product;

public interface ProductService {

    List<Product> findAllProducts();

    Product findProductById(String id) throws NotValidProductIdException, ProductNotFoundException;

    Product findProductByName(String name);

    Product saveProduct(Product product);

    List<Product> search(String param) throws NotValidProductIdException, ProductNotFoundException;

    void deleteProductById(String id) throws NotValidProductIdException, ProductNotFoundException;

    void deleteAllProducts();

    Order addProduct(List<OrderedProduct> orderedProducts, String token) throws NotValidOrderedProductList,
            UserIdNotExistException, NotValidProductIdException, ProductNotFoundException, NotValidProductQuantity;

}
