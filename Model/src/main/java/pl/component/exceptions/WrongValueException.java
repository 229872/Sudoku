package pl.component.exceptions;

public class WrongValueException extends RuntimeException {
    public WrongValueException() {
    }

    public WrongValueException(String message) {
        super(message);
    }

    public WrongValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongValueException(Throwable cause) {
        super(cause);
    }
}
