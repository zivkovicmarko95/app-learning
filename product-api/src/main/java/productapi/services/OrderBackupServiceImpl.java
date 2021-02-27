package productapi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import productapi.constants.ProductsConstants;
import productapi.models.Order;

@Service
public class OrderBackupServiceImpl implements OrderBackupService {

    /*
        Service which use Order object and send it to order-backup-api component
    */

    private final RabbitTemplate rabbitTemplate;
    private final Environment env;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public OrderBackupServiceImpl(RabbitTemplate rabbitTemplate, Environment env) {
        this.rabbitTemplate = rabbitTemplate;
        this.env = env;
    }

    @Override
    public void sendMessageToOrderBackupApi(Order order) {
        logger.info(ProductsConstants.SENDING_MESSAGE_TO_RABBITMQ);
        rabbitTemplate.convertAndSend(env.getProperty("rabbitmq.order_backup_api_product_api.exchange"),
                env.getProperty("rabbitmq.order_backup_api_product_api.routingKey"), order);
    }
    
}
