package in.banking.cbs.action_service;

import in.banking.cbs.action_service.utility.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

//@EnableMethodSecurity
@EnableFeignClients
@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class ActionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActionServiceApplication.class, args);
	}

}
