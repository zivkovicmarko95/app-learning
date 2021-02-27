package userapi.exceptions;

public class NotValidIdException extends Exception {
    
    private static final long serialVersionUID = -328767695303140909L;

    public NotValidIdException(String msg) {
        super(msg);
    }

}
