package ronaldo.command;

import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;

/**
 * Executes the "list" command to display all tasks in the task list.
 * <p>
 * This class retrieves the full list of tasks from the {@link TaskList},
 * displays them via {@link Ui}, and returns a formatted message listing the tasks.
 * </p>
 */
public class ListExecutor implements CommandExecutor {

    /**
     * Executes the list command by showing all tasks in the task list.
     *
     * @param taskList the list of tasks to display
     * @param storage  the storage instance (not modified by this command)
     * @param ui       the UI instance for displaying the task list
     * @return a string message containing all tasks in the list
     * @throws RonaldoException if an unexpected error occurs during execution
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws RonaldoException {
        String tasks = taskList.listTasks();
        ui.showTaskList(tasks);
        return "Here are the tasks in your list:\n" + tasks;
    }
}
