package com.example.productapi.exceptions;

public class OrderNotFoundException extends Exception {
    
    /*
        Exception whhich is thrown if user tries to search for order which does not
        exist or has invalid order id
    */

    private static final long serialVersionUID = 1L;

    public OrderNotFoundException(String msg) {
        super(msg);
    }

}
