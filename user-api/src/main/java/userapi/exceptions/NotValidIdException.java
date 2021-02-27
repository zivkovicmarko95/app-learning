package userapi.exceptions;

public class NotValidIdException extends Exception {
    
    /**
     * Exception which is thrown if user enter id which is invalid
     * e.g used in the methods like findById and deleteById
     */

    private static final long serialVersionUID = -328767695303140909L;

    public NotValidIdException(String msg) {
        super(msg);
    }

}
