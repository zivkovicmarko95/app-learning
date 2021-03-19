package com.example.productapi.services;

import java.util.List;

import com.example.productapi.exceptions.OrderNotFoundException;
import com.example.productapi.models.Order;

public interface OrderService {
    
    Order findOrderById(String id) throws OrderNotFoundException;

    List<Order> findAllOrders();
    
    Order save(Order order);

}
