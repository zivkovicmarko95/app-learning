package monitoringapi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import monitoringapi.models.Monitoring;

@Repository
public interface MonitoringRepository extends MongoRepository<Monitoring, String> {
    
}
