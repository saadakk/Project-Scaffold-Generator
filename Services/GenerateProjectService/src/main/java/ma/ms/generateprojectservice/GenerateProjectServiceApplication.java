package ma.ms.generateprojectservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GenerateProjectServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenerateProjectServiceApplication.class, args);
	}

}
