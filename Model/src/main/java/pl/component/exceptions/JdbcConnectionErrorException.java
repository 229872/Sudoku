package pl.component.exceptions;

public class JdbcConnectionErrorException extends RuntimeException {
    public JdbcConnectionErrorException() {
    }

    public JdbcConnectionErrorException(String message) {
        super(message);
    }

    public JdbcConnectionErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcConnectionErrorException(Throwable cause) {
        super(cause);
    }

    public JdbcConnectionErrorException(String message, Throwable cause,
                                        boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
