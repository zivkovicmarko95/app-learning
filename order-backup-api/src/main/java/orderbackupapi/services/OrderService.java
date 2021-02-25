package orderbackupapi.services;

import orderbackupapi.exceptions.NotCompleteOrderedProductObject;
import orderbackupapi.exceptions.NotCompletedOrderObject;
import orderbackupapi.exceptions.ObjectIdNotValidException;
import orderbackupapi.models.BackupOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    
    Flux<BackupOrder> findAll();

    Mono<BackupOrder> findById(String id) throws ObjectIdNotValidException;

    Mono<BackupOrder> save(BackupOrder order) throws NotCompleteOrderedProductObject, NotCompletedOrderObject;

    Mono<Void> deleteById(String id) throws ObjectIdNotValidException;

}
