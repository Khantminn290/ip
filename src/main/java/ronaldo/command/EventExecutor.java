package ronaldo.command;

import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;
import ronaldo.task.Event;

public class EventExecutor implements CommandExecutor {
    private final String description;
    private final String from;
    private final String to;

    public EventExecutor(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws RonaldoException {
        Event event = new Event(description, from, to);
        taskList.addTask(event);
        String writtenFormat = String.format("E | %s | %s | from: %s to: %s",
                event.isDone(), description, from, to);
        storage.writeTask(writtenFormat);
        ui.showAddTask(event, taskList.size());
        String message = "Got it. I've added this task:\n  " + event
                + String.format("\nNow you have %d tasks in the list.", taskList.size());
        return message;
    }
}