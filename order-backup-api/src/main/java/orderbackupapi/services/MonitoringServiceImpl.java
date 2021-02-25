package orderbackupapi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import orderbackupapi.constants.BackupConstants;
import orderbackupapi.models.Monitoring;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    /*
        Monitoring servise is used when system throws an exception and when system throws an exception
        it will create a Monitoring object and send it to monitoring-api component via RabbitMQ
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
        logger.info(BackupConstants.SENDING_MESSAGE_TO_RABBITMQ);
        rabbitTemplate.convertAndSend(env.getProperty("rabbitmq.order_backup_api.exchange"), env.getProperty("rabbitmq.order_backup_api.routingKey"), monitoring);
    }

}
