package productapi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import productapi.constants.ProductsConstants;
import productapi.models.Monitoring;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    /*
        Service which use Monitoring object and send it to monitoring-api
    */

    private final RabbitTemplate rabbitTemplate;
    private final Environment env;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public MonitoringServiceImpl(RabbitTemplate rabbitTemplate, Environment env) {
        this.rabbitTemplate = rabbitTemplate;
        this.env = env;
    }

    @Override
    public void sendMessageToMonitoringApi(Monitoring monitoring) {
        logger.info(ProductsConstants.SENDING_MESSAGE_TO_RABBITMQ);
        rabbitTemplate.convertAndSend(env.getProperty("rabbitmq.productapi.exchange"),
                env.getProperty("rabbitmq.productapi.routingKey"), monitoring);
    }

}
