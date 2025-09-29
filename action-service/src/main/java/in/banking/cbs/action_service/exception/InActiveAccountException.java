package in.banking.cbs.action_service.exception;

public class InActiveAccountException extends RuntimeException {
    public InActiveAccountException(String message) {
        super(message);
    }
}
