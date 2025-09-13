package ronaldo.exceptions;

/**
 * Base exception class for all exceptions in the Ronaldo application.
 */
public class RonaldoException extends Exception {

    /**
     * Constructs a new RonaldoException with the specified detail message.
     *
     * @param message The detail message for this exception.
     */
    public RonaldoException(String message) {
        super("Urm... " + message);
    }
}
