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
| Method | Endpoint | Component |
| ------ | ------ | ------ |
| GET | http://localhost:8010/api/user/find | user-api |
| GET | http://localhost:8010/api/user/find/{username} | user-api |
| GET | http://localhost:8010/api/monitoring/{id} | monitoring-api |
| GET | http://localhost:8010/api/monitoring | monitoring-api |
| GET | http://localhost:8010/api/order/{id} | order-backup-api |
| GET | http://localhost:8010/api/order | order-backup-api |
| GET | http://localhost:8010/api/product/find | product-api |
| GET | http://localhost:8010/api/product/find/{id} | product-api |
| GET | http://localhost:8010/api/product/search/{param} | product-api |
| GET | http://localhost:8010/api/product/category | product-api |
| GET | http://localhost:8010/api/product/category/{id} | product-api |
| POST | http://localhost:8010/api/user/register | user-api |
| POST | http://localhost:8010/api/user/login | user-api |
| POST | http://localhost:8010/api/user/update | user-api |
| POST | http://localhost:8010/api/user/resetPassword | user-api |
| POST | http://localhost:8010/api/order | order-backup-api |
| POST | http://localhost:8010/api/product | product-api |
| POST | http://localhost:8010/api/product/addproduct | product-api |
| DELETE | http://localhost:8010/api/user/delete/{id} | user-api |
| DELETE | http://localhost:8010/api/user/delete | user-api |
| DELETE | http://localhost:8010/api/monitoring | monitoring-api |
| DELETE | http://localhost:8010/api/monitoring/{id} | monitoring-api |
| DELETE | http://localhost:8010/api/order/{id} | order-backup-api |
| DELETE | http://localhost:8010/api/product/{id} | product-api |
| DELETE | http://localhost:8010/api/product/ | product-api |
| DELETE | http://localhost:8010/api/product/category/{id} | product-api |
| DELETE | http://localhost:8010/api/product/category/ | product-api |