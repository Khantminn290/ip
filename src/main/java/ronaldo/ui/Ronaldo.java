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

    public Ronaldo() {
        this.storage = new Storage();
        this.scanner = new Scanner(System.in);
        this.taskList = new TaskList(storage.load());
        this.ui = new Ui();
        ui.showGreeting();

        // sanity checks
        assert this.storage != null;
        assert this.scanner != null;
        assert this.taskList != null;
        assert this.ui != null;
    }

    /**
     * Reads and processes user input in a loop until the "bye" command is entered.
     * <p>
     * Input is parsed into a {@link Command} and dispatched to a handler method
     * that executes the appropriate action (e.g., adding tasks, marking tasks, etc.).
     */
    public void readInput() {
        String input = "";
        while (!input.equals("bye")) {
            try {
                input = scanner.nextLine();
                assert input != null; // input should never be null

                Command command = Parser.parse(input);
                assert command != null; // parser should always return a valid command

                // delegate command execution to handler
                handleCommand(command, input);

            } catch (RonaldoException r) {
                // show error message without crashing program
                ui.showError(r.getMessage());
            }
        }
    }

    /**
     * Dispatches the parsed {@link Command} to its corresponding handler method.
     *
     * @param command the parsed command from the user input
     * @param input   the raw user input string
     * @throws RonaldoException if the command is invalid or cannot be executed
     */
    private void handleCommand(Command command, String input) throws RonaldoException {
        switch (command) {
        case BYE:
            ui.showFarewell();
            break;

        case LIST:
            ui.showTaskList(taskList.listTasks());
            break;

        case MARK:
            handleMark(input, true);
            break;

        case UNMARK:
            handleMark(input, false);
            break;

        case DEADLINE:
            handleDeadline(input);
            break;

        case EVENT:
            handleEvent(input);
            break;

        case TODO:
            handleTodo(input);
            break;

        case DELETE:
            handleDelete(input);
            break;

        case FIND:
            handleFind(input);
            break;

        case INVALID:
        default:
            throw new InvalidInputException();
        }
    }

    /**
     * Handles marking or unmarking a task.
     *
     * @param input the raw user input containing the task index
     * @param mark  {@code true} to mark as done, {@code false} to unmark
     * @throws RonaldoException if the index is invalid or out of range
     */
    private void handleMark(String input, boolean mark) throws RonaldoException {
        String[] parts = input.split(" ");
        assert parts.length > 1; // must contain index
        int number = Integer.parseInt(parts[1]) - 1;
        assert number >= 0 && number < taskList.size();

        if (mark) {
            taskList.markTask(number);
            ui.showMarkedTask(taskList.getTask(number));
        } else {
            taskList.unmarkTask(number);
            ui.showUnmarkedTask(taskList.getTask(number));
        }
    }

    /**
     * Handles adding a {@link Deadline} task.
     *
     * @param input the raw user input containing description and deadline date/time
     * @throws RonaldoException if input is invalid or date format is incorrect
     */
    private void handleDeadline(String input) throws RonaldoException {
        String[] parts = input.split(" /by ");
        assert parts.length == 2; // must contain description and deadline
        String description = parts[0].replaceFirst("deadline\\s+", "").trim();
        if (description.isBlank()) throw new EmptyStringException();
        String by = parts[1].trim();
        if (by.isBlank()) throw new EmptyStringException();

        // validate date format "yyyy-MM-dd HHmm"
        try {
            var formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            java.time.LocalDateTime.parse(by, formatter);
        } catch (java.time.format.DateTimeParseException e) {
            throw new InvalidDateFormatException();
        }

        Deadline deadline = new Deadline(description, by);
        taskList.addTask(deadline);
        // persist to storage
        storage.writeTask(String.format("D | %s | %s | %s", deadline.isDone(), description, by));
        ui.showAddTask(deadline, taskList.size());
    }

    /**
     * Handles adding an {@link Event} task.
     *
     * @param input the raw user input containing description, start, and end times
     * @throws RonaldoException if input is invalid
     */
    private void handleEvent(String input) throws RonaldoException {
        String[] parts = input.split("/from|/to");
        assert parts.length == 3; // must contain description, from, and to
        String description = parts[0].replaceFirst("event\\s+", "").trim();
        if (description.isBlank()) throw new EmptyStringException();

        String from = parts[1].trim();
        String to = parts[2].trim();
        if (from.isBlank() || to.isBlank()) throw new EmptyStringException();

        Event event = new Event(description, from, to);
        taskList.addTask(event);
        // persist to storage
        storage.writeTask(String.format("E | %s | %s | %s-%s", event.isDone(), description, from, to));
        ui.showAddTask(event, taskList.size());
    }

    /**
     * Handles adding a {@link ToDo} task.
     *
     * @param input the raw user input containing description
     * @throws RonaldoException if description is missing or empty
     */
    private void handleTodo(String input) throws RonaldoException {
        String[] parts = input.split(" ", 2);
        assert parts.length == 2; // must contain keyword and description
        String description = parts[1].trim();
        if (description.isBlank()) throw new EmptyStringException();

        ToDo toDo = new ToDo(description);
        taskList.addTask(toDo);
        // persist to storage
        storage.writeTask(String.format("T | %s | %s", toDo.isDone(), description));
        ui.showAddTask(toDo, taskList.size());
    }

    /**
     * Handles deleting a task.
     *
     * @param input the raw user input containing the task index
     * @throws RonaldoException if index is invalid or out of range
     */
    private void handleDelete(String input) throws RonaldoException {
        String[] parts = input.split(" ");
        assert parts.length > 1; // must contain index
        int number = Integer.parseInt(parts[1]) - 1;
        assert number >= 0 && number < taskList.size();

        Task deletedTask = taskList.deleteTask(number);
        storage.deleteTask(number); // update storage
        ui.showDeleteTask(deletedTask, taskList.size());
    }

    /**
     * Handles finding tasks by keyword.
     *
     * @param input the raw user input containing the search keyword
     * @throws RonaldoException if keyword is missing or empty
     */
    private void handleFind(String input) throws RonaldoException {
        String keyword = input.substring(5).trim(); // extract after "find "
        if (keyword.isEmpty()) throw new EmptyStringException();

        ArrayList<Task> matchingTasks = taskList.findTasks(keyword);
        ui.showMatchingTasks(matchingTasks);
    }


    public String processInput(String input) {
        assert input != null; // input string should never be null
        try {
            Command command = Parser.parse(input);
            assert command != null;

            switch (command) {
            case BYE:
                return " Bye. I'm going to do some WingChun.";

            case LIST: {
                String tasks = taskList.listTasks();
                assert tasks != null;
                return "Here are the tasks in your list:\n" + tasks;
            }

            case MARK: {
                String[] parts = input.split(" ");
                assert parts.length > 1;
                int number = Integer.parseInt(parts[1]) - 1;
                assert number >= 0 && number < taskList.size();
                taskList.markTask(number);
                return "Nice! I've marked this task as done:\n " + taskList.getTask(number);
            }

            case UNMARK: {
                String[] parts = input.split(" ");
                assert parts.length > 1;
                int number = Integer.parseInt(parts[1]) - 1;
                assert number >= 0 && number < taskList.size();
                taskList.unmarkTask(number);
                return "OK, I've marked this task as not done yet:\n" + taskList.getTask(number);
            }

            case DEADLINE: {
                String[] parts = input.split(" /by ");
                assert parts.length == 2;
                String description = parts[0].replaceFirst("deadline\\s+", "").trim();
                if (description.isBlank()) {
                    throw new EmptyStringException();
                }
                String by = parts[1];
                assert !by.isBlank();

                try {
                    java.time.format.DateTimeFormatter formatter =
                            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                    java.time.LocalDateTime.parse(by, formatter);
                } catch (java.time.format.DateTimeParseException e) {
                    throw new InvalidDateFormatException();
                }

                Deadline deadline = new Deadline(description, by);
                assert deadline != null;
                taskList.addTask(deadline);
                String writtenFormat = String.format("D | %s | %s | %s", deadline.isDone(), description, by);
                storage.writeTask(writtenFormat);
                String message = "Got it. I've added this task:\n  " + deadline
                        + String.format("\nNow you have %d tasks in the list.", taskList.size());
                return message;
            }

            case EVENT: {
                String[] parts = input.split("/from|/to");
                assert parts.length == 3;
                String description = parts[0].replaceFirst("event\\s+", "").trim();
                if (description.isBlank()) {
                    throw new EmptyStringException();
                }
                String from = parts[1].trim();
                String to = parts[2].trim();
                assert !from.isBlank();
                assert !to.isBlank();

                Event event = new Event(description, from, to);
                assert event != null;
                taskList.addTask(event);
                String writtenFormat = String.format("E | %s | %s | %s-%s", event.isDone(), description, from, to);
                storage.writeTask(writtenFormat);
                String message = "Got it. I've added this task:\n  " + event
                        + String.format("\nNow you have %d tasks in the list.", taskList.size());
                return message;
            }

            case TODO: {
                String[] parts = input.split(" ", 2);
                assert parts.length == 2;
                String description = parts[1].trim();
                if (description.isBlank()) {
                    throw new EmptyStringException();
                }
                ToDo toDo = new ToDo(description);
                assert toDo != null;
                taskList.addTask(toDo);
                String writtenFormat = String.format("T | %s | %s", toDo.isDone(), description);
                storage.writeTask(writtenFormat);
                String message = "Got it. I've added this task:\n  " + toDo
                        + String.format("\nNow you have %d tasks in the list.", taskList.size());
                return message;
            }

            case DELETE: {
                String[] parts = input.split(" ");
                assert parts.length > 1;
                int number = Integer.parseInt(parts[1]) - 1;
                assert number >= 0 && number < taskList.size();
                Task deletedTask = taskList.deleteTask(number);
                storage.deleteTask(number);
                String message = "Noted. I've removed this task:\n  " + deletedTask
                        + String.format("\nNow you have %d tasks in the list.", taskList.size());
                return message;
            }

            case FIND: {
                String keyword = input.substring(5).trim();
                assert keyword != null;
                if (keyword.isEmpty()) {
                    throw new EmptyStringException();
                }
                ArrayList<Task> matchingTasks = taskList.findTasks(keyword);
                assert matchingTasks != null;
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

    public static void main(String[] args) {
        Ronaldo ronaldo = new Ronaldo();
        ronaldo.readInput();
    }
}

