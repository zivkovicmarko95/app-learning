package orderbackupapi.bootstrap;

import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import orderbackupapi.models.BackupOrder;
import orderbackupapi.models.OrderedProduct;
import orderbackupapi.repositories.OrderRepository;

@Component
public class Bootstrap implements CommandLineRunner {

    /*
        Bootstrap class is used only for importing dummy data in the database
    */

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private final OrderRepository orderRepository;

    @Autowired
    public Bootstrap(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();

        if (orderRepository.count().block() > 0) {
            orderRepository.deleteAll().block();
            logger.info("Deleting all the orders from the db");
        }

        BackupOrder order1 = new BackupOrder();
        order1.setOrderId(UUID.randomUUID().toString());
        order1.setUserId(UUID.randomUUID().toString());

        BackupOrder order2 = new BackupOrder();
        order2.setOrderId(UUID.randomUUID().toString());
        order2.setUserId(UUID.randomUUID().toString());

        BackupOrder order3 = new BackupOrder();
        order3.setOrderId(UUID.randomUUID().toString());
        order3.setUserId(UUID.randomUUID().toString());

        for (int i = 0; i < 5; i++) {
            OrderedProduct orderedProduct1 = new OrderedProduct(UUID.randomUUID().toString(), random.nextInt(100));
            OrderedProduct orderedProduct2 = new OrderedProduct(UUID.randomUUID().toString(), random.nextInt(100));
            OrderedProduct orderedProduct3 = new OrderedProduct(UUID.randomUUID().toString(), random.nextInt(100));

            order1.addProduct(orderedProduct1);
            order2.addProduct(orderedProduct2);
            order3.addProduct(orderedProduct3);
        }

        orderRepository.save(order1).block();
        orderRepository.save(order2).block();
        orderRepository.save(order3).block();

        logger.info("Number of elements in the database:" + orderRepository.count().block());

        logger.info("Added orders to the database");

    }
    
}
