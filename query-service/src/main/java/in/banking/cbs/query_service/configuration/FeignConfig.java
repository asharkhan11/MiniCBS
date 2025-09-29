package in.banking.cbs.query_service.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import in.banking.cbs.query_service.exception.FeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {

        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {

                String jwtToken = getJwtFromSecurityContext();

                if (jwtToken != null && !jwtToken.isBlank()) {
                    template.header("Authorization", "Bearer " + jwtToken);
                }

            }

            private String getJwtFromSecurityContext() {

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication != null && authentication.getCredentials() != null) {
                    return authentication.getCredentials().toString();
                }

                return null;
            }
        };
    }

}
