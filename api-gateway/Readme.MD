# API Gateway

In this package, there is an API gateway which is run on port 8010. It can be found in the application.properties file. In the application.properties file, also there are the ports of the other components. Parameter spring.application.name is used because of Spring Cloud Consul, which is used like discovery service, and the parameter spring.cloud.consul.discovery.health-check-interval sets the time for checking if the service is working properly or not. Other parameters are there to set paths and URIs of the other components which are used in the GatewayConfig.class. Also in application.properties are the parameters which are used for the JWT.

In this project, Spring Cloud API Gateway is used with Java-JWT, Spring Cloud Consul, Commons-Lang3, Javax Servlet API dependencies.

In the filter class (AuthorizationHeaderFilter.class) is shown that the user needs to have a valid JWT token to access almost every component of the system.