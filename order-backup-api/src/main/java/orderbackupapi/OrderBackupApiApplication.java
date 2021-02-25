package orderbackupapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderBackupApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderBackupApiApplication.class, args);
	}

}
