package monitoringapi.services;

import monitoringapi.models.Monitoring;

public interface MonitoringService extends BaseMethods<Monitoring> {
    
    void notValidCredentialsLog(String message, String api, String id);

}
