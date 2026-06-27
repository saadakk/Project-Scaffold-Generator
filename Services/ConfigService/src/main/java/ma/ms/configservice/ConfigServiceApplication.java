package ma.ms.configservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication(exclude = {
		org.springframework.cloud.vault.config.VaultAutoConfiguration.class,
		org.springframework.cloud.vault.config.VaultReactiveAutoConfiguration.class
})
@EnableConfigServer
public class ConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServiceApplication.class, args);
		//test
	}

}
