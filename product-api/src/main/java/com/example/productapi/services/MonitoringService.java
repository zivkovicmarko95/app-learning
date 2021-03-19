package com.example.productapi.services;

import com.example.productapi.models.Monitoring;

public interface MonitoringService {
    
    void sendMessageToMonitoringApi(Monitoring monitoring);
    
}
