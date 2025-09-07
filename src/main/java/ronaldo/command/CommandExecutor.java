package ronaldo.command;

import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;

/**
 * Represents a command that can be executed in the Ronaldo task manager.
 * <p>
 * Implementations of this interface define specific operations such as marking,
 * deleting, or adding tasks, and are responsible for updating the {@link TaskList},
 * persisting changes via {@link Storage}, and providing user feedback through {@link Ui}.
 * </p>
 */
public interface CommandExecutor {

    /**
     * Executes the command.
     *
     * @param taskList the list of tasks to operate on
     * @param storage  the storage instance for persisting changes
     * @param ui       the UI instance for displaying messages
     * @return a string message describing the result of the command
     * @throws RonaldoException if an error occurs during execution
     */
    String execute(TaskList taskList, Storage storage, Ui ui) throws RonaldoException;
}
