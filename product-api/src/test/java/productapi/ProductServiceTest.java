package productapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

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
import productapi.repositories.CategoryRepository;
import productapi.repositories.OrderRepository;
import productapi.repositories.ProductRepository;
import productapi.services.MonitoringServiceImpl;
import productapi.services.OrderBackupServiceImpl;
import productapi.services.OrderServiceImpl;
import productapi.services.ProductServiceImpl;
import productapi.util.JwtHelper;

@DataMongoTest
public class ProductServiceTest {

    @Autowired
    private Environment env;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private RabbitTemplate rabbitTemplate;

    private JwtHelper jwtHelper;

    private OrderBackupServiceImpl orderBackupService;
    private MonitoringServiceImpl monitoringService;
    private OrderServiceImpl orderService;
    private ProductServiceImpl productService;

    private final String productId = new ObjectId().toString();

    private final String CATEGORY_NAME = "test-name-category1";
    private final String CATEGORY_DESCRIPTION = "test-description-category";

    private final String PRODUCT_TITLE = "test-product-title";
    private final String PRODUCT_NAME = "test-product-name";
    private final String PRODUCT_DESCRIPTION = "test-product-description";
    private final double PRODUCT_PRICE = 10.0;
    private final int PRODUCT_QTY = 10;

    private String token = "Bearer ";

    @BeforeEach
    public void setUp() {
        rabbitTemplate = Mockito.mock(RabbitTemplate.class);

        monitoringService = new MonitoringServiceImpl(rabbitTemplate, env);
        orderBackupService = new OrderBackupServiceImpl(rabbitTemplate, env);

        orderService = new OrderServiceImpl(orderRepository, monitoringService);

        jwtHelper = new JwtHelper(env);

        productService = new ProductServiceImpl(categoryRepository, productRepository, orderService, orderBackupService,
                monitoringService, mongoTemplate, jwtHelper);

        if (productRepository.findByName(PRODUCT_NAME) != null) {
            Product product = productRepository.findByName(PRODUCT_NAME);
            productRepository.deleteById(product.getId());
            ;
        }

        if (categoryRepository.findByName(CATEGORY_NAME) != null) {
            Category category = categoryRepository.findByName(CATEGORY_NAME);

            if (category != null) {
                categoryRepository.deleteById(category.getId());
            }
        }

        Category category1 = new Category(CATEGORY_NAME, CATEGORY_DESCRIPTION);
        List<String> productIds = new ArrayList<>();
        productIds.add(productId);

        Product product1 = new Product(PRODUCT_TITLE, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_PRICE, PRODUCT_QTY);
        product1.setId(productId);
        product1.setCategory(category1);

        productService.saveProduct(product1);
    }

    @Test
    public void testSavingProductAndCategory()
            throws NotValidProductIdException, ProductNotFoundException, CategoryNotFoundException {
        Product product = productService.findProductById(productId);

        assertNotNull(product);
        assertNotNull(product.getCategory());

        String categoryId = product.getCategory().getId();
        Category category = productService.findCategoryById(categoryId);

        assertNotNull(category);
    }

    @Test
    public void testDeleteProductById()
            throws NotValidProductIdException, ProductNotFoundException, CategoryNotFoundException {

        Product product = productService.findProductById(productId);
        productService.deleteProductById(productId);

        assertThrows(ProductNotFoundException.class, () -> productService.findProductById(productId));

        Category category = productService.findCategoryById(product.getCategory().getId());

        assertFalse(category.getProductIds().contains(productId));
    }

    @Test
    public void testDeleteCategoryById()
            throws NotValidProductIdException, ProductNotFoundException, CategoryNotFoundException {
        Product product = productService.findProductById(productId);

        productService.deleteCategoryById(product.getCategory().getId());

        assertThrows(CategoryNotFoundException.class,
                () -> productService.findCategoryById(product.getCategory().getId()));
        assertNull(productService.findProductById(productId).getCategory());
    }

    @Test
    public void testSearch() throws NotValidProductIdException, ProductNotFoundException {
        String testProductId = new ObjectId().toString();
        assertThrows(ProductNotFoundException.class, () -> productService.search(testProductId));

        List<Product> searchedProductsByProductId = productService.search(productId);
        List<Product> searchedProductsByTitle = productService.search(PRODUCT_TITLE);
        List<Product> searchedProductsByDescription = productService.search(PRODUCT_DESCRIPTION);
        List<Product> searchedProductsByName = productService.search(PRODUCT_NAME);

        Product product = productService.findProductById(productId);
        Category category = product.getCategory();

        assertFalse(searchedProductsByDescription.isEmpty());
        assertFalse(searchedProductsByTitle.isEmpty());
        assertFalse(searchedProductsByDescription.isEmpty());
        assertFalse(searchedProductsByName.isEmpty());

        assertEquals(product, searchedProductsByProductId.get(0));
        assertEquals(product, searchedProductsByTitle.get(0));
        assertEquals(product, searchedProductsByDescription.get(0));
        assertEquals(product, searchedProductsByName.get(0));

        assertEquals(category, searchedProductsByProductId.get(0).getCategory());
        assertEquals(category, searchedProductsByTitle.get(0).getCategory());
        assertEquals(category, searchedProductsByDescription.get(0).getCategory());
        assertEquals(category, searchedProductsByName.get(0).getCategory());
    }

    @Test
    public void testAddProducts() throws NotValidOrderedProductList, UserIdNotExistException,
            NotValidProductIdException, ProductNotFoundException, NotValidProductQuantity {

        token += env.getProperty("jwt.token.testing");
        OrderedProduct validOrderedProduct = new OrderedProduct(productId, 1);
        List<OrderedProduct> validOrderedProductsList = new ArrayList<>();
        validOrderedProductsList.add(validOrderedProduct);

        OrderedProduct invalidOrderedProduct = new OrderedProduct(productId, 1000);
        List<OrderedProduct> invalidOrderedProductsList = new ArrayList<>();
        invalidOrderedProductsList.add(invalidOrderedProduct);

        Order order = productService.addProduct(validOrderedProductsList, token);

        assertNotNull(order);
        assertEquals(validOrderedProductsList, order.getOrderedProducts());

        assertThrows(NotValidProductQuantity.class, () -> productService.addProduct(invalidOrderedProductsList, token));
    }

}
