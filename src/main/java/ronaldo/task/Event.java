package ronaldo.task;

/**
 * Represents an Event task.
 * An Event is a type of Task that has a start time and an end time.
 */
public class Event extends Task {

    /** The starting time of the event. */
    protected String from;

    /** The ending time of the event. */
    protected String to;

    /**
     * Constructs an Event task with a description, start time, and end time.
     *
     * @param description the description of the event
     * @param from the start time of the event
     * @param to the end time of the event
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    /**
     * Returns the string representation of the Event task.
     * This includes a "[E]" prefix and shows the time range of the event.
     *
     * @return string representation of the Event task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + String.format(" (from: %s to: %s)", this.from, this.to);
    }
}
