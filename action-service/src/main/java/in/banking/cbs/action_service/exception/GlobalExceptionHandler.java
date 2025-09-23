package in.banking.cbs.action_service.exception;


import in.banking.cbs.action_service.message.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnAuthenticatedException.class)
    public ResponseEntity<ErrorResponse> unAuthenticatedExceptionHandler(UnAuthenticatedException e){
        ErrorResponse response = new ErrorResponse();
        response.setError(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse> unAuthorizedExceptionHandler(UnAuthorizedException e){
        ErrorResponse response = new ErrorResponse();
        response.setError(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(NotFoundException e){
        ErrorResponse response = new ErrorResponse();
        response.setError("NOT FOUND");
        response.setDetails(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}
