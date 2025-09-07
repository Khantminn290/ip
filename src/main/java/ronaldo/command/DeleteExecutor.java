package ronaldo.command;

import ronaldo.exceptions.InvalidTaskNumberException;
import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.Task;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;

/**
 * Executes the delete command by removing a task at the specified index.
 */
public class DeleteExecutor implements CommandExecutor {
    private final int index;

    /**
     * Constructs a DeleteExecutor with the specified task index.
     *
     * @param index the zero-based index of the task to delete
     */
    public DeleteExecutor(int index) {
        this.index = index;
    }

    /**
     * Executes the delete command by removing the task at the specified index
     * from both the task list and storage, then displays the result.
     *
     * @param taskList the task list containing tasks
     * @param storage the storage handler for persistent data
     * @param ui the user interface for displaying messages
     * @throws InvalidTaskNumberException if the index is invalid
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws RonaldoException {
        // Validate the index
        if (index < 0 || index >= taskList.size()) {
            throw new InvalidTaskNumberException();
        }

        // Delete the task from memory and storage
        Task deletedTask = taskList.deleteTask(index);
        storage.deleteTask(index);

        // Show confirmation message
        ui.showDeleteTask(deletedTask, taskList.size());

        String message = "Noted. I've removed this task:\n  " + deletedTask
                + String.format("\nNow you have %d tasks in the list.", taskList.size());
        return message;
    }
}
