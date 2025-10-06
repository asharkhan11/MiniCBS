package in.banking.cbs.action_service.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        String responseBody = "";
        try {
            if (response.body() != null) {
                responseBody = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);

                // parse JSON to extract "detail" field
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(responseBody);
                if (node.has("detail")) {
                    responseBody = node.get("detail").asText();
                }
            }
        } catch (IOException e) {
            responseBody = "Failed to read response body";
        }
        switch (response.status()){
            case 401 -> {
                return new UnAuthenticatedException(
                        "UnAuthenticated access for method : %s , Reason : %s".formatted(methodKey, responseBody)
                );
            }
            case 403 -> {
                return new UnAuthorizedException(
                        "Access denied to access method : %s , Reason : %s".formatted(methodKey, responseBody)
                );
            }
            case 404 -> {
                return new NotFoundException(
                        "Resource not available for method : %s , Reason : %s".formatted(methodKey, responseBody)
                );
            }
            case 500 -> {
                return new ServerServiceException(
                        "Error while calling other service for method : %s , Reason : %s".formatted(methodKey, responseBody)
                );
            }
            default -> {
                return defaultDecoder.decode(methodKey, response);
            }
        }
    }
}