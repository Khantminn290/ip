public class Ui {

    // Encase messages in a nice border
    public static String encase(String message) {
        return "____________________________________________________________\n"
                + message + "\n"
                + "____________________________________________________________\n";
    }

    // Show a greeting message
    public void showGreeting() {
        String greeting = " Hello! I'm Duke\n"
                + " What can I do for you?";
        System.out.println(encase(greeting));
    }

    // Show farewell message
    public void showFarewell() {
        String farewell = " Bye. Hope to see you again soon!";
        System.out.println(encase(farewell));
    }

    // Show loading error
    public void showLoadingError() {
        System.out.println(encase("Oops! There was an error loading your tasks."));
    }

    // Show message when a task is added
    public void showAddTask(Task task, int size) {
        String message = "Got it. I've added this task:\n  " + task
                + String.format("\nNow you have %d tasks in the list.", size);
        System.out.println(encase(message));
    }

    // Show message when a task is deleted
    public void showDeleteTask(Task task, int size) {
        String message = "Noted. I've removed this task:\n  " + task
                + String.format("\nNow you have %d tasks in the list.", size);
        System.out.println(encase(message));
    }

    // Show all tasks
    public void showTaskList(TaskList tasks) {
        String message = "Here are the tasks in your list:\n" + tasks.listTasks();
        System.out.println(encase(message));
    }

    // Show a custom message
    public void showMessage(String message) {
        System.out.println(encase(message));
    }

    // Show an error message
    public void showError(String errorMessage) {
        System.out.println(encase("Error: " + errorMessage));
    }
}
