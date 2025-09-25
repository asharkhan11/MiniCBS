package in.banking.cbs.query_service.exception;

public class AlreadyExistsException extends RuntimeException {
  public AlreadyExistsException(String message) {
    super(message);
  }
}
