package be.nelcea.unifi.exceptions;

/**
 * Java class description...
 * <p>
 * Date : 02/05/2020
 *
 * @author Eric Bariaux
 */
public class OperationFailedException extends Exception {

    public OperationFailedException() {
    }

    public OperationFailedException(String message) {
        super(message);
    }

    public OperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationFailedException(Throwable cause) {
        super(cause);
    }

    public OperationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
