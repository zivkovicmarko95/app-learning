package com.example.productapi.constants;

public class ProductsConstants {
    
    public static final String PRODUCT_API = "product-api";

    public static final String DELETED_PRODUCT_BY_ID = "Deleted the product from the database: ";
    public static final String DELETED_PRODUCTS_COLLECTION = "Deleting whole products collection from the database. ";
    public static final String DELETED_CATEGORY_BY_ID = "Deleted the category from the database: ";
    public static final String DELETED_CATEGORY_COLLECTION = "Deleting whole category collection from the database. ";

    public static final String USER_CREATED_ORDER = "INFO: Order is created. (userId, productId): ";

    public static final String PREPARING_PRODUCTS_COLLECTION = "INFO: Deleting the products collection and preparing products for adding";
    public static final String PREPARING_CATEGORY_COLLECTION = "INFO: Deleting the category collection and preparing categories for adding";
    public static final String COLLECTIONS_PREPARED = "INFO: Categories and products are added and ready to be used";

    public static final String SAVING_ORDER_TO_DATABASE = "Saving order to the database: ";

    public static final String NOT_VALID_PRODUCT_ID = "Provided id is not valid: ";
    public static final String PRODUCT_NOT_EXIST = "Product not exist. Product id is: ";
    public static final String ORDERED_PRODUCTS_ARE_NOT_VALID = "Product is out of stock. Product id is: ";
    public static final String USER_ID_NOT_EXIST = "User id is null and product can not be added and order can not be proceeded!";
    public static final String CATEGORY_NOT_FOUND = "Category is not found. Category was searched by parameter: ";
    public static final String ORDER_NOT_FOUND = "Order by passed id is not found";
    public static final String NOT_VALID_PRODUCT_QUANTITY = "Passed product is not valid. Product gty is lower then passed in ordered products";

    public static final String NO_MAPPING_FOR_URL = "There is not mapping for this URL";
    public static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";
    public static final String INTERNAL_SERVER_ERROR_MSG = "An error occured while processing the request";
    public static final String ERROR_PROCESSING_FILE = "Error occured while processing file";
    public static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
    
    public static final String SENDING_MESSAGE_TO_RABBITMQ = "Sending message to RabbitMQ and monitoring api";

    public static final String SENDING_MESSAGE_TO_ORDER_BACKUP_API = "Sending message to Backup order API via HTTP";

    public static final String PRODUCT_CREATED = "Product created and saved to the database: ";
    public static final String CATEGORY_CREATED = "Category created and saved to the database: ";
}
