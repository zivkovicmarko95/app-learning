# User API

In this package, there is a user API component which is run on port 8012. It can be found in the application.properties file. In the application.properties file, there are rabbitmq constants (queue, exchange, routingkey) parameters and also jwt token parameters. 

The name of the component for registering it via Spring Cloud Consul is User-API.

This component is connected with monitoring-api component via RabbitMQ and the product-API component will send a message to the monitoring-API component if the system throws an exception. 

Dependencies which are used in the Monitoring component are Spring Boot Starter AMQP, Spring Boot Starter Data MongoDB, Spring Boot Starter Security, Spring Boot Starter Web, Spring Cloud Starter Consul Discovery, Java JWT, Commons Lang3.

User-API is used for authentication and authorization and this component provides jwt token to the user.

Endpoint for user-api components are:
| Method | Endpoint |
| ------ | ------ |
| GET | http://localhost:8012/api/user/find |
| GET | http://localhost:8012/api/user/find/{username} |
| POST | http://localhost:8012/api/user/register |
| POST | http://localhost:8012/api/user/login |
| POST | http://localhost:8012/api/user/update |
| POST | http://localhost:8012/api/user/resetPassword |
| DELETE | http://localhost:8012/api/user/delete/{id} |
| DELETE | http://localhost:8012/api/user/delete |
 