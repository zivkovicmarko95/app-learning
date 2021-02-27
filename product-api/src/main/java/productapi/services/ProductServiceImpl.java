package productapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import productapi.constants.ProductsConstants;
import productapi.exceptions.CategoryNotFoundException;
import productapi.exceptions.NotValidProductIdException;
import productapi.exceptions.NotValidProductQuantity;
import productapi.exceptions.NotValidOrderedProductList;
import productapi.exceptions.ProductNotFoundException;
import productapi.exceptions.UserIdNotExistException;
import productapi.models.Category;
import productapi.models.Monitoring;
import productapi.models.Order;
import productapi.models.OrderedProduct;
import productapi.models.Product;
import productapi.repositories.CategoryRepository;
import productapi.repositories.ProductRepository;
import productapi.util.JwtHelper;

@Service
public class ProductServiceImpl implements CategoryService, ProductService {

    /*
     * Service which is used for working with products and the functions which are
     * provided in this service are finding all the products, finding product by id,
     * finding product by name, saving product to the database, delete product by
     * id, delete all the products, add product (buy it), find category by id, find
     * all the categories, find category by name, save category to the database,
     * delete category by id, delete all the categories and search for the products
     * by specific parameter
     */

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final OrderBackupService orderBackupService;
    private final MonitoringService monitoringService;
    private final MongoTemplate mongoTemplate;
    private final JwtHelper jwtHelper;

    @Autowired
    public ProductServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository,
            OrderService orderService, OrderBackupService orderBackupService, MonitoringService monitoringService,
            MongoTemplate mongoTemplate, JwtHelper jwtHelper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.orderService = orderService;
        this.orderBackupService = orderBackupService;
        this.monitoringService = monitoringService;
        this.mongoTemplate = mongoTemplate;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(String id) throws NotValidProductIdException, ProductNotFoundException {
        if (id == null || !ObjectId.isValid(id)) {
            throw new NotValidProductIdException(ProductsConstants.NOT_VALID_PRODUCT_ID + id);
        }

        Optional<Product> optional = productRepository.findById(id);

        if (optional.isPresent()) {
            return optional.get();
        }

        throw new ProductNotFoundException(ProductsConstants.PRODUCT_NOT_EXIST + id);
    }

    @Override
    public Product findProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product saveProduct(Product product) {
        Category category = findCategoryByName(product.getCategory().getName());
        Product saveProduct = null;

        if (category != null) {
            product.setCategory(category);

            saveProduct = productRepository.save(product);

            category.add(saveProduct.getId());
            categoryRepository.save(category);

            sendMessageToMonitoring(ProductsConstants.PRODUCT_CREATED + saveProduct.toString());

            return saveProduct;
        } else {
            saveProduct = productRepository.save(product);

            Category newCategory = new Category();
            newCategory.setName(product.getCategory().getName());
            newCategory.setDescription(product.getCategory().getDescription());

            List<String> productIds = new ArrayList<>();
            productIds.add(saveProduct.getId());

            newCategory.setProductIds(productIds);

            Category savedCategory = saveCategory(newCategory);

            saveProduct.setCategory(savedCategory);
            saveProduct = productRepository.save(product);

            sendMessageToMonitoring(ProductsConstants.PRODUCT_CREATED + saveProduct.toString());

            return saveProduct;
        }
    }

    @Override
    public void deleteProductById(String id) throws NotValidProductIdException, ProductNotFoundException {
        Product product = findProductById(id);

        if (product != null) {
            Category category = product.getCategory();
            List<String> productIds = category.getProductIds();

            for (int i = 0; i < productIds.size(); i++) {
                if (id == productIds.get(i)) {
                    productIds.remove(i);
                }
            }

            categoryRepository.save(category);
        }

        productRepository.deleteById(id);

        sendMessageToMonitoring(ProductsConstants.DELETED_PRODUCT_BY_ID + id);

        logger.warn(ProductsConstants.DELETED_PRODUCT_BY_ID + id);
    }

    @Override
    public void deleteAllProducts() {
        List<Category> categories = findAllCategories();
        productRepository.deleteAll();

        for (int i = 0; i < categories.size(); i++) {
            categories.get(i).setProductIds(null);
            ;
        }

        categoryRepository.saveAll(categories);

        sendMessageToMonitoring(ProductsConstants.DELETED_PRODUCTS_COLLECTION);

        logger.warn(ProductsConstants.DELETED_PRODUCTS_COLLECTION);
    }

    @Override
    public Order addProduct(List<OrderedProduct> orderedProducts, String token) throws NotValidOrderedProductList,
            UserIdNotExistException, NotValidProductIdException, ProductNotFoundException, NotValidProductQuantity {
        if (!isOrederedProductsListValid(orderedProducts)) {
            throw new NotValidOrderedProductList(ProductsConstants.ORDERED_PRODUCTS_ARE_NOT_VALID);
        }

        String userId = jwtHelper.getUserId(token);

        if (userId == null) {
            throw new UserIdNotExistException(ProductsConstants.USER_ID_NOT_EXIST);
        }

        Order order = new Order(userId, orderedProducts);
        Order savedOrder = orderService.save(order);

        updateProductQuantities(orderedProducts);

        logger.info(ProductsConstants.USER_CREATED_ORDER + userId);

        orderBackupService.sendMessageToOrderBackupApi(savedOrder);

        return savedOrder;
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(String id) throws CategoryNotFoundException {
        Optional<Category> optional = categoryRepository.findById(id);

        if (optional.isPresent()) {
            return optional.get();
        }

        throw new CategoryNotFoundException(ProductsConstants.CATEGORY_NOT_FOUND + id);
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category saveCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        
        sendMessageToMonitoring(ProductsConstants.CATEGORY_CREATED + savedCategory.toString());
        
        return savedCategory;
    }

    @Override
    public void deleteCategoryById(String id)
            throws NotValidProductIdException, ProductNotFoundException, CategoryNotFoundException {
        Category category = findCategoryById(id);
        List<String> productIds = category.getProductIds();
        List<Product> products = new ArrayList<>();

        if (productIds != null) {
            for (int i = 0; i < productIds.size(); i++) {
                Product product = findProductById(productIds.get(i));
                product.setCategory(null);

                products.add(product);
            }

            productRepository.saveAll(products);
        }

        categoryRepository.deleteById(id);

        sendMessageToMonitoring(ProductsConstants.DELETED_CATEGORY_BY_ID + id);

        logger.warn(ProductsConstants.DELETED_CATEGORY_BY_ID + id);
    }

    @Override
    public void deleteAllCategories() {
        categoryRepository.deleteAll();

        sendMessageToMonitoring(ProductsConstants.DELETED_CATEGORY_COLLECTION);

        logger.warn(ProductsConstants.DELETED_CATEGORY_COLLECTION);
    }

    @Override
    public List<Product> search(String param) throws NotValidProductIdException, ProductNotFoundException {
        List<Product> foundProducts = new ArrayList<>();

        if (ObjectId.isValid(param)) {
            Product product = findProductById(param);
            foundProducts.add(product);

            return foundProducts;
        }

        Criteria criteria = new Criteria();
        String searchQuery = ".*" + param + ".*";

        criteria.orOperator(Criteria.where("name").regex(searchQuery, "i"),
                Criteria.where("title").regex(searchQuery, "i"), Criteria.where("description").regex(searchQuery, "i"));

        Query query = new Query(criteria);
        foundProducts = mongoTemplate.find(query, Product.class);

        return foundProducts;
    }

    private boolean isOrederedProductsListValid(List<OrderedProduct> orderedProducts)
            throws NotValidProductIdException, ProductNotFoundException, NotValidProductQuantity {
        if (orderedProducts.size() == 0) {
            return false;
        }

        for (int i = 0; i < orderedProducts.size(); i++) {
            OrderedProduct orderedProduct = orderedProducts.get(i);
            if (orderedProduct.getProductId() == null || orderedProduct.getQty() == 0) {
                return false;
            }

            Product product = findProductById(orderedProduct.getProductId());

            if (product.getQuantity() < orderedProduct.getQty()) {
                throw new NotValidProductQuantity(ProductsConstants.NOT_VALID_PRODUCT_QUANTITY);
            }
        }

        return true;
    }

    private void updateProductQuantities(List<OrderedProduct> orderedProducts)
            throws NotValidProductIdException, ProductNotFoundException {

        for (int i = 0; i < orderedProducts.size(); i++) {
            OrderedProduct orderedProduct = orderedProducts.get(i);
            Product product = findProductById(orderedProduct.getProductId());

            product.setQuantity(product.getQuantity() - orderedProduct.getQty());

            saveProduct(product);
        }
    }

    private void sendMessageToMonitoring(String message) {
        Monitoring monitoring = new Monitoring(message, ProductsConstants.PRODUCT_API);
        monitoringService.sendMessageToMonitoringApi(monitoring);
    }

}
