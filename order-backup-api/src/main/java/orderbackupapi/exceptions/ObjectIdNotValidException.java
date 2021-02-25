package orderbackupapi.exceptions;

public class ObjectIdNotValidException extends Exception {
    
    /*
        Exception which is thrown if the invalid object id is passed, e.g if user tries to find
        some order which does not exist in the database
    */

    private static final long serialVersionUID = -7008292622495300370L;

    public ObjectIdNotValidException(String msg) {
        super(msg);
    }

}
