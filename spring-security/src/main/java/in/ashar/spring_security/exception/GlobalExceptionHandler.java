package in.ashar.spring_security.exception;

import in.ashar.spring_security.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnAuthenticatedException.class)
    public ResponseEntity<ErrorResponseDto> unAuthenticatedExceptionHandler(UnAuthenticatedException e){
        ErrorResponseDto response = new ErrorResponseDto();
        response.setError(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponseDto> unAuthorizedExceptionHandler(UnAuthorizedException e){
        ErrorResponseDto response = new ErrorResponseDto();
        response.setError(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> notFoundException(NotFoundException e){
        ErrorResponseDto response = new ErrorResponseDto();
        response.setError("NOT FOUND");
        response.setDetails(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}
