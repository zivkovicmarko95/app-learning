package productapi.exceptions;

public class UserIdNotExistException extends Exception {
    
    /*
        Exception whhich is thrown if user tries to add the product via
        POST addproduct and JWT is not parsed properly (userId does not exist)
    */

    private static final long serialVersionUID = 2106316117697020320L;

    public UserIdNotExistException(String msg) {
        super(msg);
    }

}
