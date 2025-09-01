package ronaldo.ui;

/**
 * Exception thrown when the user enters an invalid or unrecognized command.
 */
public class InvalidInputException extends RonaldoException {

    /**
     * Constructs a new InvalidInputException with a default message.
     */
    public InvalidInputException() {
        super("I'm sorry I don't quite understand you, input a valid command or I will Han Tam you.");
    }
}
