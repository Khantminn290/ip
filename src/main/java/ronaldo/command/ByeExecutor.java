package ronaldo.command;

import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;

public class ByeExecutor implements CommandExecutor{

    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws RonaldoException {
        ui.showFarewell();
        return "Bye. I'm going to do some WingChun";
    }
}
