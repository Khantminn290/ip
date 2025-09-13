package ronaldo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void testConstructor_initialValuesToDo() {
        Task task = new ToDo("Read book");
        task.setPriority(Priority.LOW);

        assertEquals("Read book", task.getDescription());
        assertFalse(task.isDone());
        assertEquals(" ", task.getStatusIcon());
        assertEquals("[T][ ] Read book (priority: Low)", task.toString());
    }

    @Test
    public void testMarkAsDone() {
        Task task = new ToDo("Do homework");
        task.setPriority(Priority.HIGH);

        task.markAsDone();
        assertTrue(task.isDone());
        assertEquals("X", task.getStatusIcon());
        assertEquals("[T][X] Do homework (priority: High)", task.toString());
    }

    @Test
    public void testUnmark() {
        Task task = new ToDo("Go jogging");
        task.setPriority(Priority.MEDIUM);

        task.markAsDone(); // mark first
        assertTrue(task.isDone());

        task.unmark(); // then unmark
        assertFalse(task.isDone());
        assertEquals(" ", task.getStatusIcon());
        assertEquals("[T][ ] Go jogging (priority: Medium)", task.toString());
    }

    @Test
    public void testDeadlineFormatting() {
        Task deadline = new Deadline("Submit report", "2025-09-15 1800");
        deadline.setPriority(Priority.HIGH);

        assertEquals("[D][ ] Submit report (priority: High) (by: 15 September 2025 6:00 pm)", deadline.toString());
    }

    @Test
    public void testEventRawStrings() {
        Task event = new Event("Concert", "2025-09-13 1400", "2025-09-13 1600");
        event.setPriority(Priority.LOW);

        assertEquals("[E][ ] Concert (priority: Low) (from: 2025-09-13 1400 to: 2025-09-13 1600)", event.toString());
    }
}
