# APP-LEARNING

This application is made of six components:
 - API Gateway (port: 8010)
 - Monitoring API (port: 8011)
 - User API (port: 8012)
 - Product API (port: 8013)
 - Backup-Order API (port: 8014)
 - Spring Cloud Consul (port: 8500)
  
Components like Monitoring API, User API, Product API and Backup-Order API are connected via RabbitMQ. Dependencies which are used in this project are Spring Cloud Starter Gateway, Spring Boot Starter AMQP, Spring Boot Starter Data MongoDB, Spring Boot Starter Data MongoDB Reactive, Spring Boot Starter Security, Spring Boot Starter Web, Spring Cloud Consul Discovery, Java JWT, Commons Lang 3, Javax Servlet API.

Endpoints for the system when all the components are started are:
| Method | Endpoint |
| ------ | ------ |
| GET | http://localhost:8010/api/user/find |
| GET | http://localhost:8010/api/user/find/{username} |
| GET | http://localhost:8010/api/monitoring/{id} |
| GET | http://localhost:8010/api/monitoring |
| GET | http://localhost:8010/api/order/{id} |
| GET | http://localhost:8010/api/order |
| GET | http://localhost:8010/api/product/find |
| GET | http://localhost:8010/api/product/find/{id} |
| GET | http://localhost:8010/api/product/search/{param} |
| GET | http://localhost:8010/api/product/category |
| GET | http://localhost:8010/api/product/category/{id} |
| POST | http://localhost:8010/api/user/register |
| POST | http://localhost:8010/api/user/login |
| POST | http://localhost:8010/api/user/update |
| POST | http://localhost:8010/api/user/resetPassword |
| POST | http://localhost:8010/api/order |
| POST | http://localhost:8010/api/product |
| POST | http://localhost:8010/api/product/addproduct |
| DELETE | http://localhost:8010/api/user/delete/{id} |
| DELETE | http://localhost:8010/api/user/delete |
| DELETE | http://localhost:8010/api/monitoring |
| DELETE | http://localhost:8010/api/monitoring/{id} |
| DELETE | http://localhost:8010/api/order/{id} |
| DELETE | http://localhost:8010/api/product/{id} |
| DELETE | http://localhost:8010/api/product/ |
| DELETE | http://localhost:8010/api/product/category/{id} |
| DELETE | http://localhost:8010/api/product/category/ |