package ronaldo.exceptions;

public class InvalidEventTaskException extends RonaldoException {
    public InvalidEventTaskException() {
        super("Input a valid Event task - event <task name> </from time /to time>");
    }
}
