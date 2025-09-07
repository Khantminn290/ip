package ronaldo.exceptions;

public class InvalidEventTaskException extends RonaldoException {
    public InvalidEventTaskException() {
        super("Input a valid Event task - event </from yyyy-mm-dd HHmm /to time>");
    }
}
