package ronaldo.exceptions;

public class InvalidTaskNumberException extends RonaldoException {
    public InvalidTaskNumberException() {
        super("Please input a valid task number.");
    }
}
