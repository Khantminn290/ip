package ronaldo.command;

import ronaldo.exceptions.InvalidTaskNumberException;
import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;

public class MarkExecutor implements CommandExecutor {
    private final int index;
    private final boolean isMark;

    public MarkExecutor(int index, boolean isMark) {
        this.index = index;
        this.isMark = isMark;
    }

    @Override
    public void execute(TaskList taskList, Storage storage, Ui ui) throws RonaldoException {
        // Validate the index, Guard
        if (index < 0 || index >= taskList.size()) {
            throw new InvalidTaskNumberException();
        }

        if (isMark) {
            taskList.markTask(index);
            storage.markTask(index);
            ui.showMarkedTask(taskList.getTask(index));
        } else {
            taskList.unmarkTask(index);
            storage.unmarkTask(index);
            ui.showUnmarkedTask(taskList.getTask(index));
        }
    }
}