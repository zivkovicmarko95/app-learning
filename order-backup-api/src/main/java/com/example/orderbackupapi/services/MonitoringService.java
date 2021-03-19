package com.example.orderbackupapi.services;

import com.example.orderbackupapi.models.Monitoring;

public interface MonitoringService {
    
    void sendMessageToMonitoringApi(Monitoring monitoring);

}
