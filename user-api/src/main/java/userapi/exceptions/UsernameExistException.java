package userapi.exceptions;

public class UsernameExistException extends Exception {

    /*
        Exception which is thrown is username exist in the database
        E.g If user wants to create an account and enter username which exist in the database,
        this exception will be thrown
    */

    private static final long serialVersionUID = -9212542490458015295L;

    public UsernameExistException(String msg) {
        super(msg);
    }

}
