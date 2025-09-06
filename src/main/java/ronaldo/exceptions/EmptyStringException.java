package ronaldo.exceptions;

/**
 * Exception thrown when a task description is empty.
 */
public class EmptyStringException extends RonaldoException {

    /**
     * Constructs an EmptyStringException with a default error message.
     */
    public EmptyStringException() {
        super("The description of task cannot be empty");
    }
}
