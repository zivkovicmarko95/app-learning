package orderbackupapi.services;

import orderbackupapi.models.Monitoring;

public interface MonitoringService {
    
    void sendMessageToMonitoringApi(Monitoring monitoring);

}
