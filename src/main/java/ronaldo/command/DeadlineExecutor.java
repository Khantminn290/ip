package ronaldo.command;

import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;
import ronaldo.task.Deadline;
import ronaldo.task.Event;
import ronaldo.task.ToDo;

public class DeadlineExecutor implements CommandExecutor {
    private final String description;
    private final String by;

    public DeadlineExecutor(String description, String by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws RonaldoException {
        Deadline deadline = new Deadline(description, by);
        taskList.addTask(deadline);
        String writtenFormat = String.format("D | %s | %s | %s",
                deadline.isDone(), description, deadline.getBy());
        storage.writeTask(writtenFormat);
        ui.showAddTask(deadline, taskList.size());

        String message = "Got it. I've added this task:\n  " + deadline
                + String.format("\nNow you have %d tasks in the list.", taskList.size());
        return message;
    }
}