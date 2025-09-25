package in.banking.cbs.query_service;

import in.banking.cbs.query_service.utility.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class QueryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueryServiceApplication.class, args);
	}

}
