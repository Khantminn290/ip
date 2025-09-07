package ronaldo.command;

import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.TaskList;
import ronaldo.task.ToDo;
import ronaldo.ui.Ui;

/**
 * Executes the "todo" command to add a new ToDo task to the task list.
 * <p>
 * This class creates a {@link ToDo} task with the given description,
 * adds it to the {@link TaskList}, persists it to {@link Storage},
 * and displays a confirmation message via {@link Ui}.
 * </p>
 */
public class TodoExecutor implements CommandExecutor {

    /** The description of the ToDo task. */
    private final String description;

    /**
     * Constructs a new {@code TodoExecutor} with the specified description.
     *
     * @param description the description of the ToDo task
     */
    public TodoExecutor(String description) {
        this.description = description;
    }

    /**
     * Executes the todo command by creating and adding the task.
     * <p>
     * Adds the new {@link ToDo} task to the {@link TaskList}, writes it to {@link Storage},
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
        ToDo toDo = new ToDo(description);
        taskList.addTask(toDo);
        String writtenFormat = String.format("T | %s | %s", toDo.isDone(), description);
        storage.writeTask(writtenFormat);
        ui.showAddTask(toDo, taskList.size());
        String message = "Got it. I've added this task:\n  " + toDo
                + String.format("\nNow you have %d tasks in the list.", taskList.size());
        return message;
    }
}
