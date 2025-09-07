package ronaldo.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 * A {@code Deadline} task contains a description, a due date, and a due time.
 * <p>
 * The expected input format for the deadline is:
 * <pre>
 *     yyyy-MM-dd HHmm
 * </pre>
 * Example: {@code "2025-08-28 2359"}.
 * </p>
 */
public class Deadline extends Task {
    /** The raw deadline string in the format {@code yyyy-MM-dd HHmm}. */
    private final String by;

    /** The date component of the deadline. */
    private final LocalDate date;

    /** The time component of the deadline. */
    private final LocalTime time;

    /** The deadline formatted as {@code MM-dd-yyyy}. */
    private final String formattedBy;

    /**
     * Constructs a {@code Deadline} task with the given description and deadline string.
     *
     * @param description The description of the task.
     * @param by The deadline in {@code yyyy-MM-dd HHmm} format.
     * @throws IllegalArgumentException if {@code by} does not follow the expected format.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;

        try {
            String[] dateTime = by.split(" ");
            this.date = LocalDate.parse(dateTime[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            this.time = LocalTime.parse(dateTime[1], DateTimeFormatter.ofPattern("HHmm"));

            this.formattedBy = this.date.format(DateTimeFormatter.ofPattern("d MMMM yyyy"))
                    + " "
                    + this.time.format(DateTimeFormatter.ofPattern("h:mm a"));
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid deadline format. Expected format: yyyy-MM-dd HHmm", e
            );
        }
    }

    /**
     * Returns the raw deadline string (e.g., {@code "2025-08-28 2359"}).
     *
     * @return The deadline string in {@code yyyy-MM-dd HHmm} format.
     */
    public String getBy() {
        return by;
    }

    /**
     * Returns a string representation of the deadline task.
     *
     * @return A formatted string in the format:
     *         {@code [D]<TaskString>(by: dd-MM-yyyy HHmm)}.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formattedBy + ")";
    }
}
