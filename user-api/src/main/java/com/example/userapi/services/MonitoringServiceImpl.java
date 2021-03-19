package com.example.userapi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.userapi.constants.MessagesConstants;
import com.example.userapi.models.Monitoring;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    /*
        Service which use Monitoring object and send it to monitoring-api
    */

    private final RabbitTemplate rabbitTemplate;
    private final Environment env;

    private final Logger logger = LoggerFactory.getLogger(MonitoringServiceImpl.class);

    @Autowired
    public MonitoringServiceImpl(RabbitTemplate rabbitTemplate, Environment env) {
        this.rabbitTemplate = rabbitTemplate;
        this.env = env;
    }

    @Override
    public void sendMessageToMonitoringApi(Monitoring monitoring) {
        logger.info(MessagesConstants.SENDING_MESSAGE_TO_RABBITMQ);
        rabbitTemplate.convertAndSend(env.getProperty("rabbitmq.userapi.exchange"), env.getProperty("rabbitmq.userapi.routingKey"), monitoring);
    }
    
}
