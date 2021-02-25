package productapi.exceptions;

public class ProductNotFoundException extends Exception {
    
    /*
    Exception whhich is thrown if user tries to search for product which
    does not have valid id or does not exist
    */

    private static final long serialVersionUID = -2341303982821225788L;

    public ProductNotFoundException(String msg) {
        super(msg);
    }

}
