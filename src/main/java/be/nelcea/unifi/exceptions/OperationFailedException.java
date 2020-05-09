package be.nelcea.unifi.exceptions;

/**
 * <p>Thrown to indicate that the requested operation could not be completed.
 *
 * @author Eric Bariaux
 */
public class OperationFailedException extends Exception {

    /**
     * Constructs an {@code OperationFailedException} with {@code null} as its error detail message.
     */
    public OperationFailedException() {
    }

    /**
     * Constructs an {@code OperationFailedException} with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link Throwable#getMessage()} method)
     */
    public OperationFailedException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code OperationFailedException} with the specified detail message and cause.
     * Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated into this exception's detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link Throwable#getMessage()} method)
     * @param cause The cause (which is saved for later retrieval by the {@link Throwable#getCause()} method).
     *              (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public OperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code OperationFailedException} with the specified cause and a detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of {@code cause}).
     * @param cause The cause (which is saved for later retrieval by the {@link Throwable#getCause()} method).
     *              (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public OperationFailedException(Throwable cause) {
        super(cause);
    }

}
