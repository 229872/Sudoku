package pl.component.exceptions;

import java.io.IOException;

public class ReadFileException extends RuntimeException {
    public ReadFileException() {
    }

    public ReadFileException(String message) {
        super(message);
    }

    public ReadFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadFileException(Throwable cause) {
        super(cause);
    }
}