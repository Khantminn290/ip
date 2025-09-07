package ronaldo.task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ronaldo.exceptions.InvalidTaskNumberException;
import ronaldo.exceptions.RonaldoException;

/**
 * Represents a list of tasks and provides operations to manage them.
 * Supports adding, deleting, retrieving, marking, unmarking, and listing tasks.
 */
public class TaskList {

    /** The list of tasks managed by this TaskList. */
    private final ArrayList<Task> tasks;

    /**
     * Constructs a {@code TaskList} with the given tasks.
     *
     * @param tasks the initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) throws IndexOutOfBoundsException {
        return tasks.remove(index);
    }

    public Task getTask(int index) throws IndexOutOfBoundsException {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public void markTask(int index) throws RonaldoException {
        tasks.get(index).markAsDone();
    }

    public void unmarkTask(int index) throws RonaldoException {
        tasks.get(index).unmark();
    }

    /**
     * Returns a string representation of all tasks in the list.
     * Each task is numbered starting from 1.
     *
     * @return a formatted string of tasks, or a message if the list is empty.
     */
    public String listTasks() {
        if (tasks.isEmpty()) {
            return "Your task list is empty!";
        }
        return tasks.stream()
                .map(task -> String.format("%d. %s", tasks.indexOf(task) + 1, task))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Returns a list of tasks whose description contains the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return ArrayList of matching tasks.
     */
    public ArrayList<Task> findTasks(String keyword) {
        return tasks.stream()
                .filter(task -> task.getDescription().contains(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
