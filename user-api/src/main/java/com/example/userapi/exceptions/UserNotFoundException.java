package com.example.userapi.exceptions;

public class UserNotFoundException extends Exception {

    /*
        Exception which will be thrown is user cannot be found in the database
    */

    private static final long serialVersionUID = -1403539090277029866L;

    public UserNotFoundException(String msg) {
        super(msg);
    }

}
