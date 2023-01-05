package pl.component.exceptions;

public class JDBCConnectionErrorException extends RuntimeException {
    public JDBCConnectionErrorException() {
    }

    public JDBCConnectionErrorException(String message) {
        super(message);
    }

    public JDBCConnectionErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDBCConnectionErrorException(Throwable cause) {
        super(cause);
    }

    public JDBCConnectionErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
