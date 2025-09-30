package in.banking.cbs.action_service.configuration;

import in.banking.cbs.action_service.security.JwtWebClientFilter;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .filter(JwtWebClientFilter.addJwtToken())
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(c ->
                                c
                                        .defaultCodecs()
                                        .maxInMemorySize(1024 * 1024 * 20)
                        )
                        .build())
                .build();
    }
}

