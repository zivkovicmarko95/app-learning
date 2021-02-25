package productapi.services;

import productapi.models.Monitoring;

public interface MonitoringService {
    
    void sendMessageToMonitoringApi(Monitoring monitoring);
    
}
