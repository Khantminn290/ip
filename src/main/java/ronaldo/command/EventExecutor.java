package ronaldo.command;

import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.Event;
import ronaldo.task.Priority;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;

/**
 * Executes the "event" command to add a new Event task to the task list.
 * <p>
 * This class creates an {@link Event} task with the given description, start time, and end time,
 * adds it to the {@link TaskList}, persists it to {@link Storage}, and displays
 * a confirmation message via {@link Ui}.
 * </p>
 */
public class EventExecutor implements CommandExecutor {

    /** The description of the event task. */
    private final String description;

    /** The start time of the event. */
    private final String from;

    /** The end time of the event. */
    private final String to;

    private final Priority priority;

    /**
     * Constructs a new {@code EventExecutor} with the specified description, start time, and end time.
     *
     * @param description the description of the event
     * @param from        the start time of the event
     * @param to          the end time of the event
     */
    public EventExecutor(String description, String from, String to, Priority priority) {
        this.description = description;
        this.from = from;
        this.to = to;
        this.priority = priority;
    }

    /**
     * Executes the event command by creating and adding the task.
     * <p>
     * Adds the new {@link Event} task to the {@link TaskList}, writes it to {@link Storage},
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
        Event event = new Event(description, from, to);
        event.setPriority(priority);
        taskList.addTask(event);
        String writtenFormat = String.format("E | %s | %s | %s | %s - %s",
                event.isDone(), priority, description, from, to);
        storage.writeTask(writtenFormat);
        //ui.showAddTask(event, taskList.size());
        String message = "Got it. I've added this task:\n  " + event
                + String.format("\nNow you have %d tasks in the list.", taskList.size());
        return message;
    }
}
