package monitoringapi.services;

import java.util.List;

import monitoringapi.models.Monitoring;

public interface MonitoringService {
    
    Monitoring findById(String id);
    
    List<Monitoring> findAll();
    
    List<Monitoring> findByApi(String api);
    
    Monitoring save(Monitoring object);
    
    void deleteById(String id);

    void deleteByApi(String api);
    
    void deleteAll();

    void notValidCredentialsLog(String message, String api, String id);

}
