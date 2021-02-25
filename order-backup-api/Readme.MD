# Order-Backup API

In this package, there is a order-backup API component which is run on port 8014. It can be found in the application.properties file. In the application.properties file, there is rabbitmq constants (queue, exchange, routingkey) parameters and also jwt token parameters. 

The name of the component for registering it via Spring Cloud Consul is Order-Backup-API.

This component is connected with monitoring-api component via RabbitMQ and the product-API component. This component will send the message to RabbitMQ if the system throws an exception and this component will recieve message from product-api component if the order is created. 

Dependencies which are used in the Monitoring component are Spring Boot Starter AMQP, Spring Boot Starter Data MongoDB Reactive, Spring Boot Starter Security, Spring Cloud Starter Consul Discovery, Java JWT, Commons Lang3.

Endpoints for order-backup-api components are:
| Method | Endpoint |
| ------ | ------ |
| GET | http://localhost:8014/api/order/{id} |
| GET | http://localhost:8014/api/order |
| POST | http://localhost:8014/api/order |
| DELETE | http://localhost:8014/api/order/{id} |