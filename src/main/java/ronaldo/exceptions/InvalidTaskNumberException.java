package ronaldo.exceptions;

/**
 * Exception thrown when a task number provided by the user is invalid.
 * <p>
 * This typically occurs when the user enters a non-numeric value,
 * a negative number, or a number that does not correspond
 * to any existing task in the task list.
 * </p>
 */
public class InvalidTaskNumberException extends RonaldoException {

    /**
     * Constructs a new {@code InvalidTaskNumberException} with a
     * default error message prompting the user to input a valid task number.
     */
    public InvalidTaskNumberException() {
        super("Please input a valid task number.");
    }
}