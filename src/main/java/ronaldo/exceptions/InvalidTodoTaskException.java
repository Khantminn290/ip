package ronaldo.exceptions;

/**
 * Exception thrown when a todo task input is invalid.
 * <p>
 * A valid todo task must follow the format:
 * {@code todo <task name>}.
 * </p>
 */
public class InvalidTodoTaskException extends RonaldoException {

    /**
     * Constructs a new {@code InvalidTodoTaskException} with a
     * default error message describing the correct format.
     */
    public InvalidTodoTaskException() {
        super("Input a valid Todo task - todo task_name /priority");
    }
}
