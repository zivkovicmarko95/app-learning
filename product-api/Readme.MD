# Product API

In this package, there is a product API component which is run on port 8013. It can be found in the application.properties file. In the application.properties file, there is rabbitmq constants (queue, exchange, routingkey) parameters and also jwt token parameters. 

The name of the component for registering it via Spring Cloud Consul is Product-API.

This component is connected with monitoring-api component via RabbitMQ and the product-API component will send a message to the monitoring-API component if the system throws an exception. 

Dependencies which are used in the Monitoring component are Spring Boot Starter AMQP, Spring Boot Starter Data MongoDB, Spring Boot Starter Security, Spring Cloud Starter Consul Discovery, Java JWT, Commons Lang3.

Endpoints for product-api component are:
| Method | Endpoint |
| ------ | ------ |
| GET | http://localhost:8013/api/product/find |
| GET | http://localhost:8013/api/product/find/{id} |
| GET | http://localhost:8013/api/product/search/{param} |
| GET | http://localhost:8013/api/product/category |
| GET | http://localhost:8013/api/product/category/{id} |
| POST | http://localhost:8013/api/product |
| POST | http://localhost:8013/api/product/addproduct |
| DELETE | http://localhost:8013/api/product/{id} |
| DELETE | http://localhost:8013/api/product/ |
| DELETE | http://localhost:8013/api/product/category/{id} |
| DELETE | http://localhost:8013/api/product/category/ |
