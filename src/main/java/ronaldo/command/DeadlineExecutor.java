package ronaldo.command;

import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.Deadline;
import ronaldo.task.Priority;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;

/**
 * Executes the "deadline" command to add a new Deadline task to the task list.
 * <p>
 * This class creates a {@link Deadline} task with the given description and due date,
 * adds it to the {@link TaskList}, persists it to {@link Storage}, and displays
 * a confirmation message via {@link Ui}.
 * </p>
 */
public class DeadlineExecutor implements CommandExecutor {

    private final String description;
    private final Priority priority;
    private final String by;

    /**
     * Constructs a new {@code DeadlineExecutor} with the specified description and due date.
     *
     * @param description the description of the deadline task
     * @param by          the due date/time of the deadline task
     */
    public DeadlineExecutor(String description, Priority priority, String by) {
        this.description = description;
        this.priority = priority;
        this.by = by;
    }

    /**
     * Executes the deadline command by creating and adding the task.
     * <p>
     * Adds the new {@link Deadline} task to the {@link TaskList}, writes it to {@link Storage},
     * and displays a confirmation message via {@link Ui}.
     * </p>
     *
     * @param taskList the list of tasks to add to
     * @param storage  the storage instance for persisting the task
     * @param ui       the UI instance for displaying messages
     * @return a string message confirming the addition of the task and showing the current task count
     * @throws RonaldoException if an error occurs during task creation or storage
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws RonaldoException {
        Deadline deadline = new Deadline(description, by);
        deadline.setPriority(priority);
        taskList.addTask(deadline);
        String writtenFormat = String.format("D | %s | %s | %s | %s",
                deadline.isDone(), priority, description, deadline.getBy());
        storage.writeTask(writtenFormat);
        ui.showAddTask(deadline, taskList.size());

        String message = "Got it. I've added this task:\n  " + deadline
                + String.format("\nNow you have %d tasks in the list.", taskList.size());
        return message;
    }
}
