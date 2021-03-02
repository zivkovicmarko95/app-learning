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
| HTTP Method | Endpoint | Component |
| ------ | ------ | ------ |
| GET | http://localhost:8010/api/users | user-api |
| GET | http://localhost:8010/api/users/{id} | user-api |
| GET | http://localhost:8010/api/monitorings/{id} | monitoring-api |
| GET | http://localhost:8010/api/monitorings | monitoring-api |
| GET | http://localhost:8010/api/orders/{id} | order-backup-api |
| GET | http://localhost:8010/api/orders | order-backup-api |
| GET | http://localhost:8010/api/products | product-api |
| GET | http://localhost:8010/api/products/{id} | product-api |
| GET | http://localhost:8010/api/products/search/{param} | product-api |
| GET | http://localhost:8010/api/products/categories | product-api |
| GET | http://localhost:8010/api/products/categories/{id} | product-api |
| POST | http://localhost:8010/api/users/register | user-api |
| POST | http://localhost:8010/api/users/login | user-api |
| POST | http://localhost:8010/api/users/update | user-api |
| POST | http://localhost:8010/api/users/resetPassword | user-api |
| POST | http://localhost:8010/api/orders | order-backup-api |
| POST | http://localhost:8010/api/products | product-api |
| POST | http://localhost:8010/api/products/addproduct | product-api |
| DELETE | http://localhost:8010/api/users/{id} | user-api |
| DELETE | http://localhost:8010/api/users | user-api |
| DELETE | http://localhost:8010/api/monitorings | monitoring-api |
| DELETE | http://localhost:8010/api/monitorings/{id} | monitoring-api |
| DELETE | http://localhost:8010/api/orders/{id} | order-backup-api |
| DELETE | http://localhost:8010/api/products/{id} | product-api |
| DELETE | http://localhost:8010/api/products/ | product-api |
| DELETE | http://localhost:8010/api/products/categories/{id} | product-api |
| DELETE | http://localhost:8010/api/products/categories/ | product-api |