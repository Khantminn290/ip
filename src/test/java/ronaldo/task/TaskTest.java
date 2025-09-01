package ronaldo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void testConstructor_initialValues() {
        Task task = new Task("Read book");
        assertEquals("Read book", task.getDescription());
        assertFalse(task.isDone());
        assertEquals(" ", task.getStatusIcon());
        assertEquals("[ ] Read book", task.toString());
    }

    @Test
    public void testMarkAsDone() {
        Task task = new Task("Do homework");
        task.markAsDone();
        assertTrue(task.isDone());
        assertEquals("X", task.getStatusIcon());
        assertEquals("[X] Do homework", task.toString());
    }

    @Test
    public void testUnmark() {
        Task task = new Task("Go jogging");
        task.markAsDone(); // mark first
        assertTrue(task.isDone());

        task.unmark(); // then unmark
        assertFalse(task.isDone());
        assertEquals(" ", task.getStatusIcon());
        assertEquals("[ ] Go jogging", task.toString());
    }
}
