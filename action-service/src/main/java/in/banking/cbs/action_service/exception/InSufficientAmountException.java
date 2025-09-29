package in.banking.cbs.action_service.exception;

public class InSufficientAmountException extends RuntimeException {
    public InSufficientAmountException(String message) {
        super(message);
    }
}
