package pl.component.exceptions;

public class WriteDatabaseException extends RuntimeException {
    public WriteDatabaseException() {
    }

    public WriteDatabaseException(String message) {
        super(message);
    }

    public WriteDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriteDatabaseException(Throwable cause) {
        super(cause);
    }

    public WriteDatabaseException(String message, Throwable cause,
                                  boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
