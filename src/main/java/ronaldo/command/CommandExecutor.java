package ronaldo.command;

import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;

public interface CommandExecutor {
    String execute(TaskList taskList, Storage storage, Ui ui) throws RonaldoException;
}
