package in.banking.cbs.action_service.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;


public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()){
            case 401 ->{
                return new UnAuthenticatedException("UnAuthenticated access for method : %s , Reason : %s".formatted(methodKey,response.reason()));
            }
            case 403 -> {
                return new UnAuthorizedException("Access denied to access method : %s , Reason : %s".formatted(methodKey,response.reason()));
            }
            case 404 -> {
                return new NotFoundException("Resource not available for method : %s , Reason : %s".formatted(methodKey,response.reason()));
            }
            case 500 -> {
                return new ServerServiceException(" : %s , Reason : %s".formatted(methodKey,response.reason()));
            }
            default -> {
                return defaultDecoder.decode(methodKey, response);
            }
        }

    }
}