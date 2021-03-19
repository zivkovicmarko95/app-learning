package com.example.orderbackupapi.services;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.orderbackupapi.constants.BackupConstants;
import com.example.orderbackupapi.exceptions.NotCompleteOrderedProductObject;
import com.example.orderbackupapi.exceptions.NotCompletedOrderObject;
import com.example.orderbackupapi.exceptions.ObjectIdNotValidException;
import com.example.orderbackupapi.models.BackupOrder;
import com.example.orderbackupapi.models.OrderedProduct;
import com.example.orderbackupapi.repositories.OrderRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceImpl implements OrderService {

    /*
        OrderService is used to handle some of basic actions with orders which are created in 
        product-api component. 
    */

    private final OrderRepository orderRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Flux<BackupOrder> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Mono<BackupOrder> findById(String id) throws ObjectIdNotValidException {
        if (!ObjectId.isValid(id)) {
            logger.warn(BackupConstants.NOT_VALID_ID + id);
            throw new ObjectIdNotValidException(BackupConstants.NOT_VALID_ID + id);
        }

        return orderRepository.findById(id);
    }

    @Override
    public Mono<BackupOrder> save(BackupOrder order) throws NotCompleteOrderedProductObject, NotCompletedOrderObject {
        if (order.getOrderId() == null || order.getUserId() == null || order.getOrderedProducts().size() == 0) {
            logger.warn(BackupConstants.INCOMPLETE_ORDER_OBJECT_EXCEPTION + order.toString());
            throw new NotCompletedOrderObject(BackupConstants.INCOMPLETE_ORDER_OBJECT_EXCEPTION + order.toString());
        }

        for (int i = 0; i < order.getOrderedProducts().size(); i++) {
            OrderedProduct orderedProduct = order.getOrderedProducts().get(i);

            if (orderedProduct.getProductId() == null || orderedProduct.getQty() == 0) {
                logger.warn(BackupConstants.INCOMPLETE_ORDERED_PRODUCT_OBJECT_EXCEPTION + orderedProduct.toString());
                throw new NotCompleteOrderedProductObject(
                        BackupConstants.INCOMPLETE_ORDERED_PRODUCT_OBJECT_EXCEPTION + orderedProduct.toString());
            }
        }

        return orderRepository.save(order);
    }

    @Override
    public Mono<Void> deleteById(String id) throws ObjectIdNotValidException {
        if (!ObjectId.isValid(id)) {
            logger.warn(BackupConstants.NOT_VALID_ID + id);
            throw new ObjectIdNotValidException(BackupConstants.NOT_VALID_ID + id);
        }

        return orderRepository.deleteById(id);
    }

}
