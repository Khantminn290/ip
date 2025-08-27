package ronaldo.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList(new ArrayList<>());
    }

    @Test
    public void testAddTaskIncreasesSize() {
        Task task = new Task("Read book");
        taskList.addTask(task);

        assertEquals(1, taskList.size());
        assertEquals(task, taskList.getTask(0));
    }

    @Test
    public void testDeleteTaskRemovesAndReturnsCorrectTask() {
        Task t1 = new Task("Task 1");
        Task t2 = new Task("Task 2");
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
        Task task = new Task("Do laundry");
        taskList.addTask(task);

        assertEquals(task, taskList.getTask(0));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.getTask(1));
    }

    @Test
    public void testMarkAndUnmarkTask() {
        Task task = new Task("Run 5km");
        taskList.addTask(task);

        taskList.markTask(0);
        assertTrue(taskList.getTask(0).isDone());
        assertEquals("[X] Run 5km", taskList.getTask(0).toString());

        taskList.unmarkTask(0);
        assertFalse(taskList.getTask(0).isDone());
        assertEquals("[ ] Run 5km", taskList.getTask(0).toString());
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
        Task t1 = new Task("First task");
        Task t2 = new Task("Second task");
        taskList.addTask(t1);
        taskList.addTask(t2);

        String expected = "1. [ ] First task\n2. [ ] Second task";
        assertEquals(expected, taskList.listTasks());
    }

    @Test
    public void testGetAllTasksReturnsReference() {
        Task t1 = new Task("Change oil");
        taskList.addTask(t1);

        ArrayList<Task> tasksRef = taskList.getAllTasks();
        assertEquals(1, tasksRef.size());

        // Modify the returned list directly -> should reflect in TaskList
        tasksRef.add(new Task("Hacky task"));
        assertEquals(2, taskList.size());
    }
}
