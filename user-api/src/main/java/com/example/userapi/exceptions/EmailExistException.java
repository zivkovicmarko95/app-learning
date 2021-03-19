package com.example.userapi.exceptions;

public class EmailExistException extends Exception {

    /*
        Exception which is thrown if user tries to add email which exist in the database
        E.g if user wants to create an account and enter email which exist in the database
    */

    private static final long serialVersionUID = -3215001625578627355L;

    public EmailExistException(String msg) {
        super(msg);
    }

}
