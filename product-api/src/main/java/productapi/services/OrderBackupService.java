package productapi.services;

import productapi.models.Order;

public interface OrderBackupService {

    void sendMessageToOrderBackupApi(Order order);
    
}
