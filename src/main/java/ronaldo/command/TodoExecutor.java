package ronaldo.command;

import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;
import ronaldo.task.ToDo;

public class TodoExecutor implements CommandExecutor {
    private final String description;

    public TodoExecutor(String description) {
        this.description = description;
    }

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