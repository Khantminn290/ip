package ronaldo.command;

import java.util.ArrayList;

import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.Task;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;

public class FindExecutor implements CommandExecutor{
    String keyword;

    public FindExecutor(String kw) {
        this.keyword = kw;
    }

    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws RonaldoException {
        ArrayList<Task> matchingTasks = taskList.findTasks(keyword);
        ui.showMatchingTasks(matchingTasks);

        if (matchingTasks.isEmpty()) {
            return "No matching tasks found in your list.";
        }
        StringBuilder tasksBuilder = new StringBuilder();
        tasksBuilder.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            tasksBuilder.append(" ").append(i + 1).append(".").append(matchingTasks.get(i)).append("\n");
        }
        return tasksBuilder.toString().trim();
    }
}
