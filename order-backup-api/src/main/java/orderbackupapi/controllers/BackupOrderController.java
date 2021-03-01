package orderbackupapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import orderbackupapi.exceptions.NotCompleteOrderedProductObject;
import orderbackupapi.exceptions.NotCompletedOrderObject;
import orderbackupapi.exceptions.ObjectIdNotValidException;
import orderbackupapi.models.BackupOrder;
import orderbackupapi.services.OrderService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(BackupOrderController.BASE_URL)
public class BackupOrderController {

    /*
        BackupOrder Controller is used for handling users HTTP requests and user can
        check all the orders, find order by id, also user can create the order and delete
        order by id
    */

    public static final String BASE_URL = "/api/orders";

    private final OrderService orderService;

    @Autowired
    public BackupOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<BackupOrder>> findAllOrders() {
        Flux<BackupOrder> orders = orderService.findAll();
        List<BackupOrder> ordersList = orders.collectList().block();

        return new ResponseEntity<>(ordersList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BackupOrder> findOrderById(@PathVariable String id) throws ObjectIdNotValidException {
        Mono<BackupOrder> monoOrder = orderService.findById(id);
        BackupOrder order = monoOrder.block();

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BackupOrder> saveOrder(@RequestBody BackupOrder order)
            throws NotCompleteOrderedProductObject, NotCompletedOrderObject {
        Mono<BackupOrder> savedOrderMono = orderService.save(order);
        BackupOrder savedOrder = savedOrderMono.block();

        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable String id) throws ObjectIdNotValidException {
        orderService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
