package ronaldo.exceptions;

public class InvalidTodoTaskException extends RonaldoException {
    public InvalidTodoTaskException() {
        super("Input a valid Todo task - todo <task name>.");
    }
}
