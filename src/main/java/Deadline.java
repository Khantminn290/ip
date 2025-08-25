import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public String getDate() {
        try {
            // split input into date and time
            String[] date_time = this.by.split(" ");

            // parse date (assume format yyyy-MM-dd)
            LocalDate date = LocalDate.parse(date_time[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // parse time (assume format HHmm, e.g. 1800)
            LocalTime time = LocalTime.parse(date_time[1], DateTimeFormatter.ofPattern("HHmm"));

            // format
            String dateFormatted = date.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
            String timeFormatted = time.format(DateTimeFormatter.ofPattern("hh:mm a"));

            return dateFormatted + ", " + timeFormatted;

        } catch (Exception e) {
            return this.by; // fallback if parsing fails
        }
    }
    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by:" + by + ")";
    }
}