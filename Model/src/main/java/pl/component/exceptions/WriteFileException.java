package pl.component.exceptions;

public class WriteFileException extends RuntimeException {

    public WriteFileException() {
    }

    public WriteFileException(String message) {
        super(message);
    }

    public WriteFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriteFileException(Throwable cause) {
        super(cause);
    }
}
