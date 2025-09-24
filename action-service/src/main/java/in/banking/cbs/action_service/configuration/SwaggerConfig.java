package in.banking.cbs.action_service.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI configSwagger(){
        return new OpenAPI()
                .info(new Info()
                        .title("CORE BANKING SYSTEM")
                        .description("All the Apis will be manages by ASHAR BHAI")
                        .version("3.0"));
    }
}
