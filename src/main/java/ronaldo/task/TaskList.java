package ronaldo.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks and provides operations to manage them.
 * Supports adding, deleting, retrieving, marking, unmarking, and listing tasks.
 */
public class TaskList {

    /** The list of tasks managed by this TaskList. */
    private ArrayList<Task> tasks;

    /**
     * Constructs a {@code TaskList} with the given tasks.
     *
     * @param tasks the initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list by its index.
     *
     * @param index the zero-based index of the task to delete.
     * @return the task that was removed.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public Task deleteTask(int index) throws IndexOutOfBoundsException {
        return tasks.remove(index);
    }

    /**
     * Returns a task by its index.
     *
     * @param index the zero-based index of the task.
     * @return the task at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public Task getTask(int index) throws IndexOutOfBoundsException {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns all tasks in the list.
     *
     * @return the list of tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Marks a task as done by its index.
     *
     * @param index the zero-based index of the task to mark.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public void markTask(int index) throws IndexOutOfBoundsException {
        tasks.get(index).markAsDone();
    }

    /**
     * Unmarks a task by its index.
     *
     * @param index the zero-based index of the task to unmark.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public void unmarkTask(int index) throws IndexOutOfBoundsException {
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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, tasks.get(i)));
        }
        return sb.toString().trim();
    }
}
