package ronaldo.ui;

import java.util.Scanner;

import ronaldo.task.Task;
import ronaldo.task.TaskList;
import ronaldo.task.Deadline;
import ronaldo.task.Event;
import ronaldo.task.ToDos;

import ronaldo.storage.Storage;

import ronaldo.parser.Parser;


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
                    String description = parts[0].replaceFirst("deadline\\s+", "").trim();
                    if (description.isBlank()) {
                        throw new EmptyStringException();
                    }
                    String by = parts[1].trim();
                    Deadline deadline = new Deadline(description, by);
                    taskList.addTask(deadline);
                    String written_format = String.format("D | %s | %s | %s", deadline.isDone(), description, by);
                    storage.writeTask(written_format);
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
                    String written_format = String.format("E | %s | %s | %s-%s", event.isDone(), description, from, to);
                    storage.writeTask(written_format);
                    ui.showAddTask(event, taskList.size());
                    break;
                }

                case TODO: {
                    String description = input.split(" ", 2)[1].trim();
                    if (description.isBlank()) {
                        throw new EmptyStringException();
                    }
                    ToDos toDo = new ToDos(description);
                    taskList.addTask(toDo);
                    String written_format = String.format("T | %s | %s", toDo.isDone(), description);
                    storage.writeTask(written_format);
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
     * Starts the Ronaldo application.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        Ronaldo ronaldo = new Ronaldo();
        ronaldo.readInput();
    }
}

