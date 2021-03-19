package com.example.productapi.models;

public class Monitoring {
    
    /*
        Monitoring object used for checking the status of the whole system
    */
    
    private String message;
    private String api;

    public Monitoring(String message, String api) {
        this.message = message;
        this.api = api;
    }

    public String getMessage() {
        return this.message;
    }

    public String getApi() {
        return this.api;
    }

    @Override
    public String toString() {
        return "{" +
            " message='" + getMessage() + "'" +
            ", api='" + getApi() + "'" +
            "}";
    }

}
