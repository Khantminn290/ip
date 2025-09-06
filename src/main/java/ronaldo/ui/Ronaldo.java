package ronaldo.ui;

import java.util.ArrayList;
import java.util.Scanner;

import ronaldo.exceptions.EmptyStringException;
import ronaldo.exceptions.InvalidDateFormatException;
import ronaldo.exceptions.InvalidInputException;
import ronaldo.exceptions.RonaldoException;
import ronaldo.parser.Parser;
import ronaldo.storage.Storage;
import ronaldo.task.Deadline;
import ronaldo.task.Event;
import ronaldo.task.Task;
import ronaldo.task.TaskList;
import ronaldo.task.ToDo;

/**
 * The main class for the Ronaldo task manager application.
 * Handles initialization, user input, command parsing, task management,
 * and interaction with the storage and UI components.
 */
public class Ronaldo {

    /** The list of tasks managed by the application. */
    private TaskList taskList;

    /** The scanner used for reading user input. */
    private Scanner scanner;

    /** The storage component responsible for saving and loading tasks. */
    private Storage storage;

    /** The UI component for displaying messages to the user. */
    private Ui ui;

    /**
     * Constructs a {@code Ronaldo} application instance.
     * Initializes storage, loads existing tasks, sets up input reading,
     * and displays a greeting message.
     */
    public Ronaldo() {
        this.storage = new Storage();
        this.scanner = new Scanner(System.in);
        this.taskList = new TaskList(storage.load());
        this.ui = new Ui();
        ui.showGreeting();
    }

    /**
     * Reads and processes user input until the "bye" command is entered.
     * Parses commands, executes the corresponding actions,
     * updates storage, and interacts with the UI.
     * Handles exceptions by displaying error messages through the UI.
     */
    public void readInput() {
        String input = "";
        while (!input.equals("bye")) {
            try {
                input = scanner.nextLine();
                Command command = Parser.parse(input);

                switch (command) {
                case BYE:
                    this.ui.showFarewell();
                    return;

                case LIST:
                    this.ui.showTaskList(this.taskList.listTasks());
                    break;

                case MARK: {
                    int number = Integer.parseInt(input.split(" ")[1]) - 1;
                    taskList.markTask(number);
                    ui.showMarkedTask(taskList.getTask(number));
                    break;
                }

                case UNMARK: {
                    int number = Integer.parseInt(input.split(" ")[1]) - 1;
                    taskList.unmarkTask(number);
                    ui.showUnmarkedTask(taskList.getTask(number));
                    break;
                }

                case DEADLINE: {
                    String[] parts = input.split(" /by ");
                    String description = parts[0];
                    if (description.isBlank()) {
                        throw new EmptyStringException();
                    }
                    String by = parts[1];

                    // Check date and time format yyyy-MM-dd HHmm
                    try {
                        java.time.format.DateTimeFormatter formatter =
                                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                        java.time.LocalDateTime.parse(by, formatter);
                    } catch (java.time.format.DateTimeParseException e) {
                        throw new InvalidDateFormatException();
                    }

                    Deadline deadline = new Deadline(description, by);
                    taskList.addTask(deadline);
                    String writtenFormat = String.format("D | %s | %s | %s", deadline.isDone(), description, by);
                    storage.writeTask(writtenFormat);
                    ui.showAddTask(deadline, taskList.size());
                    break;
                }

                case EVENT: {
                    String[] parts = input.split("/from|/to");
                    String description = parts[0].replaceFirst("event\\s+", "").trim();
                    if (description.isBlank()) {
                        throw new EmptyStringException();
                    }
                    String from = parts[1].trim();
                    String to = parts[2].trim();
                    Event event = new Event(description, from, to);
                    taskList.addTask(event);
                    String writtenFormat = String.format("E | %s | %s | %s-%s", event.isDone(), description, from, to);
                    storage.writeTask(writtenFormat);
                    ui.showAddTask(event, taskList.size());
                    break;
                }

                case TODO: {
                    String description = input.split(" ", 2)[1].trim();
                    if (description.isBlank()) {
                        throw new EmptyStringException();
                    }
                    ToDo toDo = new ToDo(description);
                    taskList.addTask(toDo);
                    String writtenFormat = String.format("T | %s | %s", toDo.isDone(), description);
                    storage.writeTask(writtenFormat);
                    ui.showAddTask(toDo, taskList.size());
                    break;
                }

                case DELETE: {
                    int number = Integer.parseInt(input.split(" ")[1]) - 1;
                    Task deletedTask = taskList.deleteTask(number);
                    ui.showDeleteTask(deletedTask, taskList.size());
                    storage.deleteTask(number);
                    break;
                }

                case FIND: {
                    String keyword = input.substring(5).trim(); // extract after "find "
                    if (keyword.isEmpty()) {
                        throw new EmptyStringException();
                    }
                    ArrayList<Task> matchingTasks = taskList.findTasks(keyword);
                    ui.showMatchingTasks(matchingTasks);
                    break;
                }

                case INVALID:
                default:
                    throw new InvalidInputException();
                }

            } catch (RonaldoException r) {
                ui.showError(r.getMessage());
            }
        }
    }

    /**
     * Processes a single user input string and returns the resulting message.
     *
     * @param input the user input string, e.g., from userInput.getText()
     * @return the output message to be handled elsewhere
     */
    public String processInput(String input) {
        try {
            Command command = Parser.parse(input);

            switch (command) {
            case BYE:
                return " Bye. I'm going to do some WingChun.";

            case LIST: {
                String tasks = taskList.listTasks();
                return "Here are the tasks in your list:\n" + tasks;
            }

            case MARK: {
                int number = Integer.parseInt(input.split(" ")[1]) - 1;
                taskList.markTask(number);
                return "Nice! I've marked this task as done:\n " + taskList.getTask(number);
            }

            case UNMARK: {
                int number = Integer.parseInt(input.split(" ")[1]) - 1;
                taskList.unmarkTask(number);
                return "OK, I've marked this task as not done yet:\n" + taskList.getTask(number);
            }

            case DEADLINE: {
                String[] parts = input.split(" /by ");
                String description = parts[0].replaceFirst("deadline\\s+", "").trim();
                if (description.isBlank()) {
                    throw new EmptyStringException();
                }
                String by = parts[1];

                try {
                    java.time.format.DateTimeFormatter formatter =
                            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                    java.time.LocalDateTime.parse(by, formatter);
                } catch (java.time.format.DateTimeParseException e) {
                    throw new InvalidDateFormatException();
                }

                Deadline deadline = new Deadline(description, by);
                taskList.addTask(deadline);
                String writtenFormat = String.format("D | %s | %s | %s", deadline.isDone(), description, by);
                storage.writeTask(writtenFormat);
                String message = "Got it. I've added this task:\n  " + deadline
                        + String.format("\nNow you have %d tasks in the list.", taskList.size());
                return message;
            }

            case EVENT: {
                String[] parts = input.split("/from|/to");
                String description = parts[0].replaceFirst("event\\s+", "").trim();
                if (description.isBlank()) {
                    throw new EmptyStringException();
                }
                String from = parts[1].trim();
                String to = parts[2].trim();
                Event event = new Event(description, from, to);
                taskList.addTask(event);
                String writtenFormat = String.format("E | %s | %s | %s-%s", event.isDone(), description, from, to);
                storage.writeTask(writtenFormat);
                String message = "Got it. I've added this task:\n  " + event
                        + String.format("\nNow you have %d tasks in the list.", taskList.size());
                return message;
            }

            case TODO: {
                String description = input.split(" ", 2)[1].trim();
                if (description.isBlank()) {
                    throw new EmptyStringException();
                }
                ToDo toDo = new ToDo(description);
                taskList.addTask(toDo);
                String writtenFormat = String.format("T | %s | %s", toDo.isDone(), description);
                storage.writeTask(writtenFormat);
                String message = "Got it. I've added this task:\n  " + toDo
                        + String.format("\nNow you have %d tasks in the list.", taskList.size());
                return message;
            }

            case DELETE: {
                int number = Integer.parseInt(input.split(" ")[1]) - 1;
                Task deletedTask = taskList.deleteTask(number);
                storage.deleteTask(number);
                String message = "Noted. I've removed this task:\n  " + deletedTask
                        + String.format("\nNow you have %d tasks in the list.", taskList.size());
                return message;
            }

            case FIND: {
                String keyword = input.substring(5).trim(); // extract after "find "
                if (keyword.isEmpty()) {
                    throw new EmptyStringException();
                }

                ArrayList<Task> matchingTasks = taskList.findTasks(keyword);
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

            case INVALID:
            default:
                throw new InvalidInputException();
            }

        } catch (RonaldoException r) {
            return r.getMessage();
        }
    }

    /**
     * Starts the Ronaldo application.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        Ronaldo ronaldo = new Ronaldo();
        ronaldo.readInput();
    }
}
