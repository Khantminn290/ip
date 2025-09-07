package ronaldo.ui;

import java.util.ArrayList;
import java.util.Scanner;

import ronaldo.command.Command;
import ronaldo.command.CommandExecutor;
import ronaldo.exceptions.EmptyStringException;
import ronaldo.exceptions.InvalidDateFormatException;
import ronaldo.exceptions.InvalidDeadlineTaskException;
import ronaldo.exceptions.InvalidEventTaskException;
import ronaldo.exceptions.InvalidInputException;
import ronaldo.exceptions.InvalidTaskNumberException;
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

    public void readInput() {
        String input = "";
        while (!input.equals("bye")) {
            try {
                input = scanner.nextLine();
                if (input == null || input.trim().isEmpty()) {
                    continue;
                }

                CommandExecutor executor = Parser.parse(input);
                executor.execute(taskList, storage, ui);

                // Check if it was a bye command
                if (input.equals("bye")) {
                    return;
                }

            } catch (RonaldoException r) {
                ui.showError(r.getMessage());
            } catch (Exception e) {
                ui.showError("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public String processInput(String input) {
        assert input != null; // input string should never be null
        try {
            Command command = Parser.parse2(input);
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
                storage.markTask(number);
                return "Nice! I've marked this task as done:\n " + taskList.getTask(number);
            }

            case UNMARK: {
                String[] parts = input.split(" ");
                assert parts.length > 1;
                int number = Integer.parseInt(parts[1]) - 1;
                assert number >= 0 && number < taskList.size();
                taskList.unmarkTask(number);
                storage.unmarkTask(number);
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

