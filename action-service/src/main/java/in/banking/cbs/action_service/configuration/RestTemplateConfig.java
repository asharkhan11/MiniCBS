package in.banking.cbs.action_service.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.stream.Collectors;

@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {

        return new RestTemplateBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(10))
                .errorHandler(new CustomResponseErrorHandler())
                .build();
    }


    static class CustomResponseErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            HttpStatusCode statusCode = response.getStatusCode();
            return statusCode.is4xxClientError() || statusCode.is5xxServerError();
        }


        @Override
        public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {

            String responseBody = new BufferedReader(
                    new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));


            if (response.getStatusCode().is4xxClientError()) {

                throw new RuntimeException("""
                        Client Error: %s
                        Method: %s
                        url: %s
                        """.formatted(responseBody, method.name(), url.getRawPath()));

            } else if (response.getStatusCode().is5xxServerError()) {

                throw new RuntimeException("""
                        Server Error: %s
                        Method: %s
                        url: %s
                        """.formatted(responseBody, method.name(), url.getRawPath()));

            }

        }

    }

}