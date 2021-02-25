# Spring Cloud Consul

In this package, there is a Spring Cloud Consul component which is run on default port 8500. This component is used as a discovery service.

Spring Cloud Consul is provided by Spring developers and it is similar tool as Eureka discovery service.

The command for starting Spring Cloud Consul is:
```sh
    consul agent -server -bootstrap-expect=1 -data-dir=consol-data -ui -bind=YOUR_IP_ADDRESS 
```

If you do not know YOUR_IP_ADDRESS param, then you just need to type:
```sh 
    ipconfig 
```