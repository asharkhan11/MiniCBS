package in.banking.cbs.action_service.exception;


import in.banking.cbs.action_service.message.ErrorResponse;
import in.banking.cbs.action_service.message.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnAuthenticatedException.class)
    public ResponseEntity<ErrorResponse> unAuthenticatedExceptionHandler(UnAuthenticatedException e){
        String message = e.getMessage();

        if(message.contains("Reason :")){
            String[] split = message.split("Reason :");

            ErrorResponse response = new ErrorResponse();
            response.setError(split[0]);
            response.setDetails(split[1]);

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        ErrorResponse response = new ErrorResponse();
        response.setError(message);

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse> unAuthorizedExceptionHandler(UnAuthorizedException e){
        String message = e.getMessage();

        if(message.contains("Reason :")){
            String[] split = message.split("Reason :");

            ErrorResponse response = new ErrorResponse();
            response.setError(split[0]);
            response.setDetails(split[1]);

            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        ErrorResponse response = new ErrorResponse();
        response.setError(message);

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(NotFoundException e){
        ErrorResponse response = new ErrorResponse();
        response.setError("NOT FOUND");
        response.setDetails(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> alreadyExistsExceptionHandler(AlreadyExistsException e){
        ErrorResponse response = new ErrorResponse();
        response.setError("ALREADY EXISTS");
        response.setDetails(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> invalidDataExceptionHandler(InvalidDataException e){
        ErrorResponse response = new ErrorResponse();
        response.setError("INVALID");
        response.setDetails(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }


    @ExceptionHandler(ServerServiceException.class)
    public ResponseEntity<ErrorResponse> serverServiceExceptionHandler(ServerServiceException e){
        String message = e.getMessage();

        if(message.contains("Reason :")){
            String[] split = message.split("Reason :");

            ErrorResponse response = new ErrorResponse();
            response.setError(split[0]);
            response.setDetails(split[1]);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ErrorResponse response = new ErrorResponse();
        response.setError("MICROSERVICE EXCEPTION");
        response.setDetails(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception e){
        ErrorResponse response = new ErrorResponse();
        response.setError("INTERNAL SERVER ERROR");
        response.setDetails(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OtpExpiredException.class)
    public ResponseEntity<ErrorResponse> otpExpiredExceptionHandler(OtpExpiredException e){
        ErrorResponse response = new ErrorResponse();
        response.setError("OTP EXPIRED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(InActiveAccountException.class)
    public ResponseEntity<ErrorResponse> inActiveAccountExceptionHandler(InActiveAccountException e){
        ErrorResponse response = new ErrorResponse();
        response.setError("INACTIVE ACCOUNT");
        response.setDetails(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(InSufficientAmountException.class)
    public ResponseEntity<ErrorResponse> inSufficientAmountExceptionHandler(InSufficientAmountException e){
        ErrorResponse response = new ErrorResponse();
        response.setError("INSUFFICIENT AMOUNT");
        response.setDetails(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
