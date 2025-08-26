package ronaldo.ui;

public class EmptyStringException extends RonaldoException{
    public EmptyStringException() {
        super("The description of task cannot be empty");
    }
}
