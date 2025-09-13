package ronaldo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList(new ArrayList<>());
    }

    @Test
    public void testAddTodoIncreasesSize() {
        Task task = new ToDo("Read book");
        task.setPriority(Priority.LOW);
        taskList.addTask(task);

        assertEquals(1, taskList.size());
        assertEquals(task, taskList.getTask(0));
        assertEquals("[T][ ] Read book (priority: Low)", task.toString());
    }

    @Test
    public void testAddDeadlineAndEvent() {
        Task deadline = new Deadline("Submit report", "2025-09-15 1800");
        deadline.setPriority(Priority.HIGH);

        Task event = new Event("Meeting", "2025-09-13 1400", "2025-09-13 1600");
        event.setPriority(Priority.MEDIUM);

        taskList.addTask(deadline);
        taskList.addTask(event);

        assertEquals(2, taskList.size());
        assertEquals("[D][ ] Submit report (priority: High) (by: 15 September 2025 6:00 pm)",
                deadline.toString());
        assertEquals("[E][ ] Meeting (priority: Medium) (from: 2025-09-13 1400 to: 2025-09-13 1600)",
                event.toString());
    }

    @Test
    public void testDeleteTaskRemovesAndReturnsCorrectTask() {
        Task t1 = new ToDo("Task 1");
        t1.setPriority(Priority.MEDIUM);

        Task t2 = new Deadline("Task 2", "2025-10-01 1200");
        t2.setPriority(Priority.LOW);

        taskList.addTask(t1);
        taskList.addTask(t2);

        Task removed = taskList.deleteTask(0);
        assertEquals(t1, removed);
        assertEquals(1, taskList.size());
        assertEquals(t2, taskList.getTask(0));
    }

    @Test
    public void testDeleteTaskInvalidIndexThrows() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.deleteTask(0));
    }

    @Test
    public void testGetTaskValidAndInvalidIndex() {
        Task task = new ToDo("Do laundry");
        task.setPriority(Priority.HIGH);
        taskList.addTask(task);

        assertEquals(task, taskList.getTask(0));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.getTask(1));
    }

    @Test
    public void testMarkAndUnmarkTask() {
        Task task = new ToDo("Run 5km");
        task.setPriority(Priority.MEDIUM);
        taskList.addTask(task);

        taskList.markTask(0);
        assertTrue(taskList.getTask(0).isDone());
        assertEquals("[T][X] Run 5km (priority: Medium)", taskList.getTask(0).toString());

        taskList.unmarkTask(0);
        assertFalse(taskList.getTask(0).isDone());
        assertEquals("[T][ ] Run 5km (priority: Medium)", taskList.getTask(0).toString());
    }

    @Test
    public void testMarkTaskInvalidIndexThrows() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.markTask(0));
    }

    @Test
    public void testListTasksEmpty() {
        assertEquals("Your task list is empty!", taskList.listTasks());
    }

    @Test
    public void testListTasksMultiple() {
        Task t1 = new ToDo("First task");
        t1.setPriority(Priority.MEDIUM);

        Task t2 = new Deadline("Second task", "2025-12-01 2359");
        t2.setPriority(Priority.LOW);

        taskList.addTask(t1);
        taskList.addTask(t2);

        String expected =
                "1. [T][ ] First task (priority: Medium)\n"
                        + "2. [D][ ] Second task (priority: Low) (by: 1 December 2025 11:59 pm)";
        assertEquals(expected, taskList.listTasks());
    }

    @Test
    public void testGetAllTasksReturnsReference() {
        Task t1 = new ToDo("Change oil");
        t1.setPriority(Priority.HIGH);
        taskList.addTask(t1);

        ArrayList<Task> tasksRef = taskList.getAllTasks();
        assertEquals(1, tasksRef.size());

        // Modify the returned list directly -> should reflect in TaskList
        Task hacky = new ToDo("Hacky task");
        hacky.setPriority(Priority.LOW);
        tasksRef.add(hacky);

        assertEquals(2, taskList.size());
    }
}
