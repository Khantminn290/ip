package ronaldo.command;

import java.util.ArrayList;

import ronaldo.exceptions.RonaldoException;
import ronaldo.storage.Storage;
import ronaldo.task.Task;
import ronaldo.task.TaskList;
import ronaldo.ui.Ui;

/**
 * Executes the "find" command to search for tasks containing a specific keyword.
 * <p>
 * This class searches the {@link TaskList} for tasks whose descriptions
 * contain the provided keyword, displays the matching tasks via {@link Ui},
 * and returns a summary message.
 * </p>
 */
public class FindExecutor implements CommandExecutor {

    /** The keyword to search for within task descriptions. */
    private String keyword;

    /**
     * Constructs a new {@code FindExecutor} with the specified keyword.
     *
     * @param kw the keyword to search for in the task list
     */
    public FindExecutor(String kw) {
        this.keyword = kw;
    }

    /**
     * Executes the find operation on the given task list.
     * <p>
     * Finds all tasks that contain the keyword in their description,
     * displays them using {@link Ui}, and returns a formatted message
     * summarizing the results.
     * </p>
     *
     * @param taskList the list of tasks to search in
     * @param storage  the storage instance (not modified by this command)
     * @param ui       the UI instance for displaying matching tasks
     * @return a string message listing matching tasks or indicating none were found
     * @throws RonaldoException if an unexpected error occurs during execution
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws RonaldoException {
        ArrayList<Task> matchingTasks = taskList.findTasks(keyword);
        //ui.showMatchingTasks(matchingTasks);

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
