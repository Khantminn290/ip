public class InvalidDateFormat extends RonaldoException {
    public InvalidDateFormat() {
        super(Ronaldo.encase("Input correct date format -> yyyy-mm-dd SIUU"));
    }
}
