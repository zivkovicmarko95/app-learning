package productapi.exceptions;

public class CategoryNotFoundException extends Exception {
    
    /* 
        Exception whhich is thrown if user tries to search to category which
        does not exist
    */

    private static final long serialVersionUID = -235575587630643867L;

    public CategoryNotFoundException(String msg) {
        super(msg);
    }

}
