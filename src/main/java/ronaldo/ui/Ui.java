package ronaldo.ui;

import java.util.ArrayList;

import ronaldo.task.Task;

/**
 * Handles all user interface interactions for the Ronaldo task manager.
 * Provides methods to display messages, greetings, farewells, errors,
 * and task-related notifications in a formatted style.
 */
public class Ui {

    /**
     * Encloses a message in a border for visual clarity.
     *
     * @param message the message to encase.
     * @return the formatted message with a border.
     */
    public static String encase(String message) {
        return "____________________________________________________________\n"
                + message + "\n"
                + "____________________________________________________________\n";
    }

    /**
     * Displays a greeting message when the application starts.
     */
    public void showGreeting() {
        String greeting = " Hello! I'm Ronaldo\n"
                + " What can I do for you? SIUU";
        System.out.println(encase(greeting));
    }

    /**
     * Displays a farewell message when the application ends.
     */
    public void showFarewell() {
        String farewell = " Bye. Hope to see you again soon! SIUU";
        System.out.println(encase(farewell));
    }

    /**
     * Displays a message when a task is added to the task list.
     *
     * @param task the task that was added.
     * @param size the total number of tasks after adding.
     */
    public void showAddTask(Task task, int size) {
        String message = "Got it. I've added this task:\n  " + task
                + String.format("\nNow you have %d tasks in the list.", size);
        System.out.println(encase(message));
    }

    /**
     * Displays a message when a task is deleted from the task list.
     *
     * @param task the task that was deleted.
     * @param size the total number of tasks after deletion.
     */
    public void showDeleteTask(Task task, int size) {
        String message = "Noted. I've removed this task:\n  " + task
                + String.format("\nNow you have %d tasks in the list.", size);
        System.out.println(encase(message));
    }

    /**
     * Displays a message when a task is marked as done.
     *
     * @param task the task that was marked.
     */
    public void showMarkedTask(Task task) {
        String message = "Nice! I've marked this task as done:\n " + task;
        System.out.println(encase(message));
    }

    /**
     * Displays a message when a task is unmarked.
     *
     * @param task the task that was unmarked.
     */
    public void showUnmarkedTask(Task task) {
        String message = "OK, I've marked this task as not done yet:\n" + task;
        System.out.println(encase(message));
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks the formatted string of all tasks.
     */
    public void showTaskList(String tasks) {
        String message = "Here are the tasks in your list:\n" + tasks;
        System.out.println(encase(message));
    }

    /**
     * Displays matching tasks found by a search keyword.
     *
     * @param matchingTasks the list of tasks that match the search criteria.
     */
    public void showMatchingTasks(ArrayList<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            String message = "No matching tasks found in your list.";
            System.out.println(encase(message));
        } else {
            StringBuilder tasksBuilder = new StringBuilder();
            tasksBuilder.append("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                tasksBuilder.append(" ").append(i + 1).append(".").append(matchingTasks.get(i)).append("\n");
            }
            System.out.println(encase(tasksBuilder.toString().trim()));
        }
    }

    /**
     * Displays an error message.
     *
     * @param errorMessage the message describing the error.
     */
    public void showError(String errorMessage) {
        System.out.println(encase(errorMessage));
    }
}
