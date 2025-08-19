public class InvalidInputException extends RonaldoException{
    public InvalidInputException() {
        super(Ronaldo.encase("I'm sorry I don't quite understand you, input a valid command."));
    }
}
