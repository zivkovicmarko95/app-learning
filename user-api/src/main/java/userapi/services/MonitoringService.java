package userapi.services;

import userapi.models.Monitoring;

public interface MonitoringService {
    
    void sendMessageToMonitoringApi(Monitoring monitoring);
    
}
