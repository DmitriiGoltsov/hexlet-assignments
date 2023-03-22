package exercise;

// BEGIN
public class NegativeRadiusException extends Exception {

    public NegativeRadiusException() {
    }

    public NegativeRadiusException(String message) {
        super(message);
    }

    public NegativeRadiusException(String message, Throwable cause) {
        super(message, cause);
    }

    public NegativeRadiusException(Throwable cause) {
        super(cause);
    }

    public NegativeRadiusException(String message, Throwable cause,
                                   boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
// END
