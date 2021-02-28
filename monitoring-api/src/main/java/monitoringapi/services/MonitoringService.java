package monitoringapi.services;

import java.util.List;

import monitoringapi.models.Monitoring;

public interface MonitoringService {
    
    Monitoring findById(String id);
    
    List<Monitoring> findAll();
    
    Monitoring save(Monitoring object);
    
    void deleteById(String id);
    
    void deleteAll();

    void notValidCredentialsLog(String message, String api, String id);

}
