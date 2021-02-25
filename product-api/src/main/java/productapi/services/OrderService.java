package productapi.services;

import java.util.List;

import productapi.exceptions.OrderNotFoundException;
import productapi.models.Order;

public interface OrderService {
    
    Order findOrderById(String id) throws OrderNotFoundException;

    List<Order> findAllOrders();
    
    Order save(Order order);

}
