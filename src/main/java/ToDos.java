public class ToDos extends Task {
    public ToDos(String d) {
        super(d);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
