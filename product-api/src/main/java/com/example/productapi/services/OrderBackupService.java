package com.example.productapi.services;

import com.example.productapi.models.Order;

public interface OrderBackupService {

    void sendMessageToOrderBackupApi(Order order);
    
}
