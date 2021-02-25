package orderbackupapi.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import orderbackupapi.models.BackupOrder;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<BackupOrder, String> {
    
}
