package ronaldo.command;

import ronaldo.exceptions.InvalidTaskNumberException;
import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;

/**
 * Executes the "mark" or "unmark" command on a task in the task list.
 * <p>
 * This class marks a task as completed or uncompleted based on the {@code isMark} flag,
 * updates the storage, and displays the corresponding message via {@link Ui}.
 * </p>
 */
public class MarkExecutor implements CommandExecutor {

    /** The zero-based index of the task to mark or unmark. */
    private final int index;

    /** {@code true} if marking the task, {@code false} if unmarking. */
    private final boolean isMark;

    /**
     * Constructs a new {@code MarkExecutor} for a specific task.
     *
     * @param index  the zero-based index of the task to mark or unmark
     * @param isMark {@code true} to mark the task as done, {@code false} to unmark
     */
    public MarkExecutor(int index, boolean isMark) {
        this.index = index;
        this.isMark = isMark;
    }

    /**
     * Executes the mark or unmark operation on the specified task list.
     * <p>
     * Validates the task index, updates the task status in both the task list
     * and storage, and shows the corresponding message via {@link Ui}.
     * </p>
     *
     * @param taskList the list of tasks to operate on
     * @param storage  the storage instance to persist changes
     * @param ui       the UI instance to display messages
     * @return a message describing the result of the operation
     * @throws RonaldoException if the task index is invalid
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws RonaldoException {
        // Validate the index, Guard
        if (index < 0 || index >= taskList.size()) {
            throw new InvalidTaskNumberException();
        }

        if (isMark) {
            taskList.markTask(index);
            storage.markTask(index);
            //ui.showMarkedTask(taskList.getTask(index));
            return "Nice! I've marked this task as done:\n " + taskList.getTask(index);
        } else {
            taskList.unmarkTask(index);
            storage.unmarkTask(index);
            //ui.showUnmarkedTask(taskList.getTask(index));
            return "OK, I've marked this task as not done yet:\n" + taskList.getTask(index);
        }
    }
}
