package ronaldo.task;

/**
 * Represents a To-Do task.
 * A To-Do is a type of Task that only has a description and does not have a date/time.
 */
public class ToDos extends Task {

    /**
     * Constructs a ToDos task with the given description.
     *
     * @param d the description of the task
     */
    public ToDos(String d) {
        super(d);
    }

    /**
     * Returns the string representation of the ToDos task.
     * This includes a "[T]" prefix to indicate it is a To-Do task.
     *
     * @return string representation of the ToDos task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
