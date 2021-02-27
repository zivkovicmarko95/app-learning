package userapi.constants;

public class MessagesConstants {

    // User Service constants
    public static final String RETURNING_FOUND_USER_BY_USERNAME = "Returning found user by username: ";
    public static final String NO_USER_FOUND_BY_USERNAME = "No user found by username: ";
    public static final String NO_USER_FOUND_BY_EMAIL = "No user found by email: ";
    public static final String USERNAME_ALREADY_EXISTS = "Username already exists: ";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists: ";
    public static final String USER_DELETED_SUCCESFULLY = "User deleted successfully: ";
    public static final String EMAIL_SENT = "New password is set to user which has email: ";

    // Jwt-Token messages
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";

    // Exception messages
    public static final String NO_MAPPING_FOR_URL = "There is not mapping for this URL";
    public static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";
    public static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
    public static final String INTERNAL_SERVER_ERROR_MSG = "An error occured while processing the request";
    public static final String INCORRECT_CREDENTIALS = "Username or password incorrect. Please try again";
    public static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration";
    public static final String ERROR_PROCESSING_FILE = "Error occured while processing file";
    public static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";
    public static final String NOT_VALID_ID = "Passed id is not valid: ";

    // RabbitMQ message
    public static final String USER_API = "user-api";
    public static final String SENDING_MESSAGE_TO_RABBITMQ = "Sending message to RabbitMQ and monitoring api";
    public static final String USER_RESTART_PASSWORD = "User restart password. User email is: ";
    public static final String USER_CREATED = "User has been created to the database";
    public static final String USER_DELETED = "WARNING: Deleted user with id: ";
    public static final String USER_COLLECTION_DELETED = "WARNING: Deleting whole collection of users!";

    // Bootstrap
    public static final String PREPERING_USER_COLLECTION_DELETING = "INFO: Deleting user collection";
    public static final String PREPERING_USER_COLLECTION_ADDING = "INFO: Adding users to the database and creating new collection";

}
 