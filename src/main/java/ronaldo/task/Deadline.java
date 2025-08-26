package ronaldo.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected String by;
    protected LocalDate date;
    protected LocalTime time;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;

        // Expecting format: "yyyy-MM-dd HHmm"
        String[] dateTime = by.split(" ");
        this.date = LocalDate.parse(dateTime[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.time = LocalTime.parse(dateTime[1], DateTimeFormatter.ofPattern("HHmm"));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by:" + by + ")";
    }
}