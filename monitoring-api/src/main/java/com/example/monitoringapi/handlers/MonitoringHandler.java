package com.example.monitoringapi.handlers;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.monitoringapi.constants.MonitoringMessage;
import com.example.monitoringapi.models.Monitoring;
import com.example.monitoringapi.services.MonitoringService;

@Component
public class MonitoringHandler {

    /*
        MonitoringHandler is handler which is used for handle all the exception messages from all the components
        If some component has an exception, the exception will be sent to the monitoring-api component and that
        message will be processed and saved to the database
    */

    private final Logger logger = LoggerFactory.getLogger(MonitoringHandler.class);
    private final MonitoringService userMonitoringService;

    @Autowired
    public MonitoringHandler(MonitoringService userMonitoringService) {
        this.userMonitoringService = userMonitoringService;
    }
    
    @RabbitListener(queues = "userapi_queue")
    public void consumeMessage(HashMap<String, Object> monitoring) {
        saveToDb(monitoring);
    }

    @RabbitListener(queues = "productapi_queue")
    public void consumeMessageProductApi(HashMap<String, Object> monitoring) {
        saveToDb(monitoring);
    }

    @RabbitListener(queues = "order_backupapi_queue")
    public void consumeMessageBackupProductApi(HashMap<String, Object> monitoring) {
        saveToDb(monitoring);
    }

    private void saveToDb(HashMap<String, Object> monitoring) {
        if (monitoring.get("api") != null && monitoring.get("message") != null) {

            final String api = monitoring.get("api").toString();
            final String message = monitoring.get("message").toString();
    
            final Monitoring userMonitoring = new Monitoring(message, api);

            logger.info(MonitoringMessage.SAVING_OBJECT_TO_DB_INFO_MSG);
            userMonitoringService.save(userMonitoring);
        }
    }

}
