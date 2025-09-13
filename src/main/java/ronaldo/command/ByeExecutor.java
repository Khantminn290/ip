package ronaldo.command;

import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;

/**
 * Executes the "bye" command to exit the Ronaldo application.
 * <p>
 * This class displays a farewell message via {@link Ui} and returns
 * a goodbye message. It does not modify the {@link TaskList} or {@link Storage}.
 * </p>
 */
public class ByeExecutor implements CommandExecutor {

    /**
     * Executes the bye command.
     *
     * @param taskList the list of tasks (not modified by this command)
     * @param storage  the storage instance (not modified by this command)
     * @param ui       the UI instance for displaying the farewell message
     * @return a goodbye message
     * @throws RonaldoException if an unexpected error occurs during execution
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws RonaldoException {
        //ui.showFarewell();
        return "Bye. I'm going to do some WingChun";
    }
}
