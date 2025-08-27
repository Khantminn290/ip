package ronaldo.ui;

public class InvalidDateFormatException extends RonaldoException {
    public InvalidDateFormatException() {
        super("Input correct date format -> yyyy-mm-dd SIUU");
    }
}