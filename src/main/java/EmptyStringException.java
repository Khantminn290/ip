public class EmptyStringException extends RonaldoException{
    public EmptyStringException() {
        super(Ronaldo.encase("The description of task cannot be empty"));
    }
}
