package productapi.controller;

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

import productapi.dto.ProductDTO;
import productapi.exceptions.CategoryNotFoundException;
import productapi.exceptions.NotValidOrderedProductList;
import productapi.exceptions.NotValidProductIdException;
import productapi.exceptions.NotValidProductQuantity;
import productapi.exceptions.ProductNotFoundException;
import productapi.exceptions.UserIdNotExistException;
import productapi.models.Category;
import productapi.models.Order;
import productapi.models.OrderedProduct;
import productapi.models.Product;
import productapi.services.ProductServiceImpl;
import productapi.util.Mapper;

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

    private final ProductServiceImpl productService;
    private final Mapper mapper;

    @Autowired
    public ProductController(ProductServiceImpl productService, Mapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAllProducts() {
        List<Product> products = productService.findAllProducts();
        return new ResponseEntity<>(mapper.convertProductToProductDTO(products), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable String id)
            throws NotValidProductIdException, ProductNotFoundException {
        Product product = productService.findProductById(id);

        return new ResponseEntity<>(mapper.convertProductToProductDTO(product), HttpStatus.OK);
    }

    @GetMapping("/search/{param}")
    public ResponseEntity<List<ProductDTO>> search(@PathVariable String param)
            throws NotValidProductIdException, ProductNotFoundException {
        List<Product> foundProducts = productService.search(param);

        if (foundProducts.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.convertProductToProductDTO(foundProducts), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        if (productDTO.getCategory().getName() != null) {
            Product product = mapper.convertDtoToProduct(productDTO);
            Product saveProduct = productService.saveProduct(product);

            return new ResponseEntity<>(saveProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id)
            throws NotValidProductIdException, ProductNotFoundException {
        productService.deleteProductById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllProducts() {
        productService.deleteAllProducts();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> findAllCategories() {
        List<Category> categories = productService.findAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable String id) throws CategoryNotFoundException {
        Category category = productService.findCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable String id)
            throws NotValidProductIdException, ProductNotFoundException, CategoryNotFoundException {
        productService.deleteCategoryById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/categories")
    public ResponseEntity<Void> deleteAllCategories() {
        productService.deleteAllCategories();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addproduct")
    public ResponseEntity<Order> addProduct(@RequestHeader HashMap<String, String> params,
            @RequestBody List<OrderedProduct> orderedProducts) throws NotValidOrderedProductList,
            UserIdNotExistException, NotValidProductIdException, ProductNotFoundException, NotValidProductQuantity {
        String token = params.get("authorization");
        Order order = productService.addProduct(orderedProducts, token);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
