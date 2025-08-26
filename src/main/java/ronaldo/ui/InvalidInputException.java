package ronaldo.ui;

public class InvalidInputException extends RonaldoException{
    public InvalidInputException() {
        super("I'm sorry I don't quite understand you, input a valid command.");
    }
}
