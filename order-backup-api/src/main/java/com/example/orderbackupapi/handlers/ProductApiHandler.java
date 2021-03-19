package com.example.orderbackupapi.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.orderbackupapi.constants.BackupConstants;
import com.example.orderbackupapi.exceptions.NotCompleteOrderedProductObject;
import com.example.orderbackupapi.exceptions.NotCompletedOrderObject;
import com.example.orderbackupapi.models.BackupOrder;
import com.example.orderbackupapi.models.OrderedProduct;
import com.example.orderbackupapi.services.OrderService;

@Component
public class ProductApiHandler {

    /*
        Handler which is used to handle message from the product-api. When user wants to buy
        a product, when the order is created in product-api component, also the same order will
        be sent to order-backup-api component and this order will be processed and saved to the
        order-backup-api database
    */

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final OrderService orderService;

    @Autowired
    public ProductApiHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = "order_backup_api_product_api_queue")
    public void consumeOrder(HashMap<String, Object> order)
            throws NotCompleteOrderedProductObject, NotCompletedOrderObject {
        
        if (isMessageValid(order)) {
        
            String orderId = order.get("id").toString();
            String userId = order.get("userId").toString();
            Object rawParsedOrderedProducts = order.get("orderedProducts");
            
            List<Object> orderedProductsList = new ArrayList<>();
            List<OrderedProduct> orderedProducts = new ArrayList<>();

            if (rawParsedOrderedProducts.getClass().isArray()) {
                orderedProductsList = Arrays.asList((Object[]) rawParsedOrderedProducts);
            } else if (rawParsedOrderedProducts instanceof Collection) {
                orderedProductsList = new ArrayList<>((Collection<?>) rawParsedOrderedProducts);
            }

            for (int i = 0; i < orderedProductsList.size(); i++) {
                LinkedHashMap<Object, Object> product = null;
                try {
                    product = (LinkedHashMap<Object, Object>) orderedProductsList.get(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                
                OrderedProduct op = new OrderedProduct(product.get("productId").toString(), Integer.parseInt(product.get("qty").toString()));
                orderedProducts.add(op);
            }
            
            BackupOrder backupOrder = new BackupOrder(orderId, userId, orderedProducts);

            BackupOrder saveOrder = orderService.save(backupOrder).block();

            logger.info(BackupConstants.ORDER_SAVED + saveOrder.toString());
        } else {
            logger.warn("Recieved message is not valid: " + order.toString());
        }
    }

    private boolean isMessageValid(HashMap<String, Object> order) {
        if (order.get("id") != null && order.get("userId") != null  && order.get("orderedProducts") != null) {
            return true;
        }

        return false;
    }
}
