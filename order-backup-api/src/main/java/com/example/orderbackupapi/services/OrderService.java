package com.example.orderbackupapi.services;

import com.example.orderbackupapi.exceptions.NotCompleteOrderedProductObject;
import com.example.orderbackupapi.exceptions.NotCompletedOrderObject;
import com.example.orderbackupapi.exceptions.ObjectIdNotValidException;
import com.example.orderbackupapi.models.BackupOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    
    Flux<BackupOrder> findAll();

    Mono<BackupOrder> findById(String id) throws ObjectIdNotValidException;

    Mono<BackupOrder> save(BackupOrder order) throws NotCompleteOrderedProductObject, NotCompletedOrderObject;

    Mono<Void> deleteById(String id) throws ObjectIdNotValidException;

}
