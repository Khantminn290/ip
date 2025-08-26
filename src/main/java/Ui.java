public class Ui {

    // Encase messages in a nice border
    public static String encase(String message) {
        return "____________________________________________________________\n"
                + message + "\n"
                + "____________________________________________________________\n";
    }

    // Show a greeting message
    public void showGreeting() {
        String greeting = " Hello! I'm Ronaldo\n"
                + " What can I do for you? SIUU";
        System.out.println(encase(greeting));
    }

    // Show farewell message
    public void showFarewell() {
        String farewell = " Bye. Hope to see you again soon! SIUU";
        System.out.println(encase(farewell));
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

    // Show message when a task is marked
    public void showMarkedTask(Task task) {
        String message = "Nice! I've marked this task as done:\n " + task;
        System.out.println(encase(message));
    }

    // Show message when a task is unmarked
    public void showUnmarkedTask(Task task) {
        String message = "OK, I've marked this task as not done yet:\n" + task;
        System.out.println(encase(message));
    }

    // Show all tasks
    public void showTaskList(String tasks) {
        String message = "Here are the tasks in your list:\n" + tasks;
        System.out.println(encase(message));
    }

    // Show an error message
    public void showError(String errorMessage) {
        System.out.println(encase("Error: " + errorMessage));
    }
}
