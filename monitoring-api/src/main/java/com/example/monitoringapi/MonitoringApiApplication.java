package com.example.monitoringapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MonitoringApiApplication {

	/*
		Anotation @EnableDiscoveryClient is used because this project uses Spring Cloud Consul and this anotation
		allows Spring Cloud Consul to find the service
	*/

	public static void main(String[] args) {
		SpringApplication.run(MonitoringApiApplication.class, args);
	}

}
