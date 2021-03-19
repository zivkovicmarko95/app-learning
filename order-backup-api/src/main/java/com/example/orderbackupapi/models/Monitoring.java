package com.example.orderbackupapi.models;

public class Monitoring {
    
    /*
        Monitoring object which is created when system throws an exception and sent via RabbitMQ 
        to the monitoring-api component
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
