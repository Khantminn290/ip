package ronaldo.command;

import jdk.jfr.Percentage;
import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;

public class ListExecutor implements CommandExecutor{

    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws RonaldoException {
        String tasks = taskList.listTasks();
        ui.showTaskList(tasks);
        return "Here are the tasks in your list:\n" + tasks;
    }}
