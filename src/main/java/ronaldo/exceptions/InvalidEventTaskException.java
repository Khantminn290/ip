package ronaldo.exceptions;

/**
 * Exception thrown when an event task input is invalid.
 * <p>
 * A valid event task must follow the format:
 * {@code event <task name> /from <start time> /to <end time>}.
 * </p>
 */
public class InvalidEventTaskException extends RonaldoException {

    /**
     * Constructs a new {@code InvalidEventTaskException} with a
     * default error message describing the correct format.
     */
    public InvalidEventTaskException() {
        super("Input a valid Event task - event task_name /from time /to time");
    }
}
