package productapi.services;

import java.util.List;

import productapi.exceptions.NotValidProductIdException;
import productapi.exceptions.NotValidProductQuantity;
import productapi.exceptions.NotValidOrderedProductList;
import productapi.exceptions.ProductNotFoundException;
import productapi.exceptions.UserIdNotExistException;
import productapi.models.Order;
import productapi.models.OrderedProduct;
import productapi.models.Product;

public interface ProductService {

    List<Product> findAllProducts();

    Product findProductById(String id) throws NotValidProductIdException, ProductNotFoundException;

    Product findProductByName(String name);

    Product saveProduct(Product product);

    List<Product> search(String param);

    void deleteProductById(String id) throws NotValidProductIdException, ProductNotFoundException;

    void deleteAllProducts();

    Order addProduct(List<OrderedProduct> orderedProducts, String token) throws NotValidOrderedProductList,
            UserIdNotExistException, NotValidProductIdException, ProductNotFoundException, NotValidProductQuantity;

}
