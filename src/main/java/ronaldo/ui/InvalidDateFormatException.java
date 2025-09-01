package ronaldo.ui;

/**
 * Exception thrown when the input date format is invalid.
 */
public class InvalidDateFormatException extends RonaldoException {

    /**
     * Constructs an InvalidDateFormatException with a default error message.
     */
    public InvalidDateFormatException() {
        super("Input correct date format -> yyyy-mm-dd HHmm SIUU");
    }
}
