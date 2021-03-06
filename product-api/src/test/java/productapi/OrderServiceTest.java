package productapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.rabbitmq.client.ConnectionFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import productapi.exceptions.OrderNotFoundException;
import productapi.models.Order;
import productapi.models.OrderedProduct;
import productapi.repositories.OrderRepository;
import productapi.services.MonitoringServiceImpl;
import productapi.services.OrderServiceImpl;

@DataMongoTest
public class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Environment env;

    private RabbitTemplate rabbitTemplate;

    private MonitoringServiceImpl monitoringService;
    private OrderServiceImpl orderService;

    private final String orderId1 = UUID.randomUUID().toString();
    private final String orderId2 = UUID.randomUUID().toString();

    @BeforeEach
    public void setUp() {
        Random random = new Random();

        rabbitTemplate = Mockito.mock(RabbitTemplate.class);

        monitoringService = new MonitoringServiceImpl(rabbitTemplate, env);
        orderService = new OrderServiceImpl(orderRepository, monitoringService);

        List<OrderedProduct> orderedProducts1 = new ArrayList<>();
        orderedProducts1.add(new OrderedProduct(UUID.randomUUID().toString(), random.nextInt(100)));
        orderedProducts1.add(new OrderedProduct(UUID.randomUUID().toString(), random.nextInt(100)));

        Order order1 = new Order(UUID.randomUUID().toString(), orderedProducts1);
        order1.setId(orderId1);

        List<OrderedProduct> orderedProducts2 = new ArrayList<>();
        orderedProducts2.add(new OrderedProduct(UUID.randomUUID().toString(), random.nextInt(100)));
        orderedProducts2.add(new OrderedProduct(UUID.randomUUID().toString(), random.nextInt(100)));

        Order order2 = new Order(UUID.randomUUID().toString(), orderedProducts2);
        order2.setId(orderId2);

        orderService.save(order1);
        orderService.save(order2);
    }

    @Test
    public void checkIfObjectIsSavedInDb() throws OrderNotFoundException {
        Order order1 = orderService.findOrderById(orderId1);
        Order order2 = orderService.findOrderById(orderId2);

        assertEquals(orderId1, order1.getId());
        assertEquals(orderId2, order2.getId());

        assertNotNull(order1);
        assertNotNull(order2);

        assertNotNull(order1.getOrderCreated());
        assertNotNull(order2.getOrderCreated());

        assertFalse(order1.getOrderedProducts().isEmpty());
        assertFalse(order2.getOrderedProducts().isEmpty());
    }

}
