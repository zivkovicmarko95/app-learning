# Monitoring API

In this package, there is a monitoring API component which is run on port 8011. It can be find in the application.properties file. In the application.properties file, there is a custom header name with a token parameter, rabbitmq constants (queue, exchange, routingkey) parameters, and also jwt token parameters. 

The name of the component for registering it via Spring Cloud Consul is Monitoring-API.

This component is connected with the whole system via RabbitMQ and receives messages if the system throws an exception in some component that will be saved in the database. 
Dependencies that are used in the Monitoring component are Spring Boot Starter AMQP, Spring Boot Starter Data MongoDB, Spring Boot Starter Security, Spring Cloud Starter Consul Discovery, Java JWT, Commons Lang3

Endpoints for monitoring-api components are:
| Method | Endpoint |
| ------ | ------ |
| GET | http://localhost:8011/api/monitoring/{id} |
| GET | http://localhost:8011/api/monitoring |
| DELETE | http://localhost:8011/api/monitoring |
| DELETE | http://localhost:8011/api/monitoring/{id} |