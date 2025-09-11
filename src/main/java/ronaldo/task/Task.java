package ronaldo.task;

/**
 * Represents a general task with a description and completion status.
 * Provides methods to mark, unmark, and retrieve task details.
 */
public class Task {

    /** The description of the task. */
    protected String description;

    /** The completion status of the task. */
    protected boolean isDone;

    /** The priority level of the task */
    protected Priority priority;

    /**
     * Constructs a {@code Task} with the given description.
     * The task is initially not done.
     *
     * @param description the description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     * "X" represents a completed task, while " " represents an incomplete task.
     *
     * @return the status icon as a string.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns whether the task is marked as done.
     *
     * @return {@code true} if the task is done, {@code false} otherwise.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Unmarks the task, setting it to not done.
     */
    public void unmark() {
        this.isDone = false;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return this.priority.toString();
    }

    /**
     * Returns the description of the task.
     *
     * @return the task description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a string representation of the task.
     * The string includes the status icon and the task description.
     *
     * @return the string representation of the task.
     */
    @Override
    public String toString() {
        return String.format("[%s] %s (priority: %s)", this.getStatusIcon(), this.description, this.getPriority());
    }
}
