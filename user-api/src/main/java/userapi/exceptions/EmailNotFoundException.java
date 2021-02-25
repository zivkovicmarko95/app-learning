package userapi.exceptions;

public class EmailNotFoundException extends Exception {

    /*
        Exception which is thrown if user enter the email which does not exist in the database
        E.g if user wants to restart password and enter invalid email
    */

    private static final long serialVersionUID = 3549231690963897480L;

    public EmailNotFoundException(String msg) {
        super(msg);
    }

}
