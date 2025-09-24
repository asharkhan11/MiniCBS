package in.banking.cbs.action_service.configuration;

import feign.codec.ErrorDecoder;
import in.banking.cbs.action_service.exception.FeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

}
