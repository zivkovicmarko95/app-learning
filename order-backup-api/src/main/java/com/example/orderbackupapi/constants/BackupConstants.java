package com.example.orderbackupapi.constants;

public class BackupConstants {

    // Api name
    public static final String ORDER_BACKUP_API = "order-backup-api";
    
    // RabbitMQ:
    public static final String SENDING_MESSAGE_TO_RABBITMQ = "Sending message to RabbitMQ and monitoring api";
    
    // Exception constants
    public static final String INCOMPLETE_ORDER_OBJECT_EXCEPTION = "Passed order object is not completed and it is not possible to save incomplete order object. Order object is: ";
    public static final String INCOMPLETE_ORDERED_PRODUCT_OBJECT_EXCEPTION = "Passed object in order object is not valid. OrderedProduct object is: ";
    public static final String NOT_VALID_ID = "Passed id for deleting object is not valid: ";

    public static final String NO_MAPPING_FOR_URL = "There is not mapping for this URL";
    public static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";
    public static final String INTERNAL_SERVER_ERROR_MSG = "An error occured while processing the request";
    public static final String ERROR_PROCESSING_FILE = "Error occured while processing file";
    public static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";

    public static final String ORDER_SAVED = "Order has been saved: ";
}
