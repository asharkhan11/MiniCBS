package in.banking.cbs.action_service.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import in.banking.cbs.action_service.message.ErrorResponseDto;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {


        switch (response.status()){
            case 401 ->{
                return new UnAuthenticatedException("UnAuthenticated access for method : %s , Reason : %s".formatted(methodKey,response.body()));
            }
            case 403 -> {
                return new UnAuthorizedException("Access denied to access method : %s , Reason : %s".formatted(methodKey,response.body()));
            }
            case 404 -> {
                return new NotFoundException("Resource not available for method : %s , Reason : %s".formatted(methodKey,response.body()));
            }
            case 500 -> {
                return new ServerServiceException("Error while calling other service for method : %s , Reason : %s".formatted(methodKey,response.body()));
            }
            default -> {
                return defaultDecoder.decode(methodKey, response);
            }
        }

    }
}