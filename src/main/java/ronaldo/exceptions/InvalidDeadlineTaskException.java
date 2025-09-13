package ronaldo.exceptions;

/**
 * Exception thrown when the user enters an invalid Deadline Task.
 */
public class InvalidDeadlineTaskException extends RonaldoException {

    /**
     * Constructs a new InvalidDeadlineException with a default message.
     */
    public InvalidDeadlineTaskException() {
        super("Input a valid Deadline task - deadline task_name /by yyyy-MM-dd HHmm /p priority ");
    }
}
