package com.example.productapi.exceptions;

public class NotValidProductIdException extends Exception {
    
    /*
        Exception whhich is thrown if user tries to search to produict which
        does not exist or has not valid id
    */

    private static final long serialVersionUID = 3652876217515336807L;

    public NotValidProductIdException(String msg) {
        super(msg);
    }

}
