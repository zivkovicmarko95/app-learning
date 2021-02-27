package productapi.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import productapi.constants.ProductsConstants;
import productapi.exceptions.OrderNotFoundException;
import productapi.models.Monitoring;
import productapi.models.Order;
import productapi.repositories.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    /*
        Service which can be used for finding all the orders or finding orders by order id 
        and also this service is used for saving the order to the database
    */
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final OrderRepository orderRepository;
    private final MonitoringService monitoringService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, MonitoringService monitoringService) {
        this.orderRepository = orderRepository;
        this.monitoringService = monitoringService;
    }

    @Override
    public Order findOrderById(String id) throws OrderNotFoundException {
        Optional<Order> optional = orderRepository.findById(id);

        if (optional.isPresent()) {
            return optional.get();
        }
        
        throw new OrderNotFoundException(ProductsConstants.ORDER_NOT_FOUND + id);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order save(Order order) {
        Order savedOrder = orderRepository.save(order);

        logger.info(ProductsConstants.SAVING_ORDER_TO_DATABASE + savedOrder.getId());
        sendMessageToMonitoring(ProductsConstants.SAVING_ORDER_TO_DATABASE + savedOrder.getId());

        return savedOrder;
    }

    private void sendMessageToMonitoring(String message) {
        Monitoring monitoring = new Monitoring(message, ProductsConstants.PRODUCT_API);
        monitoringService.sendMessageToMonitoringApi(monitoring);
    }

}
