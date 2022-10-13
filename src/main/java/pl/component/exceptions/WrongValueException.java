package pl.component.exceptions;

public class WrongValueException extends Exception {
    public WrongValueException() {
    }

    public WrongValueException(String message) {
        super(message);
    }
}
