package ronaldo.parser;

import ronaldo.command.ByeExecutor;
import ronaldo.command.Command;
import ronaldo.command.CommandExecutor;
import ronaldo.command.DeadlineExecutor;
import ronaldo.command.DeleteExecutor;
import ronaldo.command.EventExecutor;
import ronaldo.command.FindExecutor;
import ronaldo.command.ListExecutor;
import ronaldo.command.MarkExecutor;
import ronaldo.command.TodoExecutor;
import ronaldo.exceptions.EmptyStringException;
import ronaldo.exceptions.InvalidDateFormatException;
import ronaldo.exceptions.InvalidDeadlineTaskException;
import ronaldo.exceptions.InvalidEventTaskException;
import ronaldo.exceptions.InvalidInputException;
import ronaldo.exceptions.InvalidTaskNumberException;
import ronaldo.exceptions.InvalidTodoTaskException;
import ronaldo.exceptions.RonaldoException;
import ronaldo.task.Priority;

/**
 * The {@code Parser} class is responsible for interpreting raw user input
 * and converting it into executable {@link CommandExecutor} objects.
 * <p>
 * It also provides utility methods for parsing specific command types such as
 * {@code deadline}, {@code event}, {@code todo}, etc., and validates their formats.
 * </p>
 */
public class Parser {

    /**
     * Parses a raw user input string into a {@link CommandExecutor}.
     *
     * @param input the user input string
     * @return the corresponding {@link CommandExecutor}
     * @throws RonaldoException if the input is invalid or improperly formatted
     */
    public static CommandExecutor parse(String input) throws RonaldoException {
        if (input.equals("bye")) {
            return new ByeExecutor();
        } else if (input.equals("list")) {
            return new ListExecutor();
        } else if (input.startsWith("mark ")) {
            return parseMark(input, true);
        } else if (input.startsWith("unmark ")) {
            return parseMark(input, false);
        } else if (input.startsWith("deadline ")) {
            return parseDeadline(input);
        } else if (input.startsWith("event ")) {
            return parseEvent(input);
        } else if (input.startsWith("todo ")) {
            return parseTodo(input);
        } else if (input.startsWith("delete ")) {
            return parseDelete(input);
        } else if (input.startsWith("find ")) {
            return parseFind(input);
        } else {
            throw new InvalidInputException();
        }
    }

    /**
     * Parses a mark or unmark command.
     *
     * @param input  the raw user input
     * @param isMark {@code true} if it is a mark command, {@code false} if unmark
     * @return a {@link MarkExecutor} for the specified task index
     * @throws RonaldoException if the task number is missing or invalid
     */
    private static CommandExecutor parseMark(String input, boolean isMark) throws RonaldoException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new InvalidTaskNumberException();
        }
        int number = Integer.parseInt(parts[1]) - 1;
        return new MarkExecutor(number, isMark);
    }

    /**
     * Parses a deadline command with a description and due date.
     *
     * @param input the raw user input
     * @return a {@link DeadlineExecutor} containing the parsed description and due date
     * @throws RonaldoException if the format is invalid, description is empty,
     *                          or date format does not match {@code yyyy-MM-dd HHmm}
     */
    private static CommandExecutor parseDeadline(String input) throws RonaldoException {
        // Split description and "/by ..." part
        String[] parts = input.split(" /by ", 2);
        if (parts.length != 2) {
            throw new InvalidDeadlineTaskException();
        }

        String description = parts[0].replaceFirst("deadline\\s+", "").trim();
        if (description.isBlank()) {
            throw new EmptyStringException();
        }

        // Now split "/by" part into deadline and priority
        String[] byAndPriority = parts[1].split(" /priority ", 2);
        if (byAndPriority.length != 2) {
            throw new InvalidDeadlineTaskException();
        }

        String by = byAndPriority[0].trim();
        if (by.isBlank()) {
            throw new EmptyStringException();
        }

        String priorityStr = byAndPriority[1].trim();
        if (priorityStr.isBlank()) {
            throw new InvalidTodoTaskException();
        }

        // Convert to Priority (handles LOW/MEDIUM/HIGH and L/M/H)
        Priority priority;
        try {
            priority = Priority.fromString(priorityStr);
        } catch (IllegalArgumentException e) {
            throw new RonaldoException("Invalid priority: " + priorityStr
                    + ". Please use LOW/MEDIUM/HIGH or L/M/H.");
        }

        // Validate date format
        try {
            java.time.format.DateTimeFormatter formatter =
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            java.time.LocalDateTime.parse(by, formatter);
        } catch (java.time.format.DateTimeParseException e) {
            throw new InvalidDateFormatException();
        }

        return new DeadlineExecutor(description, priority, by);
    }


    /**
     * Parses an event command with a description, start time, and end time.
     *
     * @param input the raw user input
     * @return an {@link EventExecutor} containing the parsed event details
     * @throws RonaldoException if the format is invalid or fields are empty
     */
    private static CommandExecutor parseEvent(String input) throws RonaldoException {
        // First split description, from, to, and priority
        String[] parts = input.split("/from|/to|/priority");
        if (parts.length != 4) {
            throw new InvalidEventTaskException();
        }

        // Extract description
        String description = parts[0].replaceFirst("event\\s+", "").trim();
        if (description.isBlank()) {
            throw new EmptyStringException();
        }

        // Extract from, to, and priority
        String from = parts[1].trim();
        String to = parts[2].trim();
        String priorityStr = parts[3].trim();

        if (from.isBlank() || to.isBlank()) {
            throw new EmptyStringException();
        }

        if (priorityStr.isBlank()) {
            throw new InvalidTodoTaskException();
        }

        // Convert to Priority (supports LOW/MEDIUM/HIGH and L/M/H)
        Priority priority;
        try {
            priority = Priority.fromString(priorityStr);
        } catch (IllegalArgumentException e) {
            throw new RonaldoException("Invalid priority: " + priorityStr
                    + ". Please use LOW/MEDIUM/HIGH or L/M/H.");
        }

        return new EventExecutor(description, from, to, priority);
    }


    /**
     * Parses a todo command with a description.
     *
     * @param input the raw user input
     * @return a {@link TodoExecutor} containing the parsed description
     * @throws RonaldoException if the description is missing or empty
     */
    private static CommandExecutor parseTodo(String input) throws RonaldoException {
        String args = input.substring(5).trim();

        // Split into description and priority part
        String[] split = args.split("/priority", 2);
        String description = split[0].trim();

        if (description.isBlank()) {
            throw new EmptyStringException();
        }

        if (split.length < 2 || split[1].trim().isEmpty()) {
            throw new InvalidTodoTaskException();
        }

        String priorityStr = split[1].trim();
        Priority priority;
        try {
            priority = Priority.fromString(priorityStr);
        } catch (IllegalArgumentException e) {
            throw new RonaldoException("Invalid priority: " + priorityStr
                    + ". Please use LOW/MEDIUM/HIGH or L/M/H.");
        }

        return new TodoExecutor(description, priority);
    }

    /**
     * Parses a delete command with a task number.
     *
     * @param input the raw user input
     * @return a {@link DeleteExecutor} for the specified task index
     * @throws RonaldoException if the task number is missing or invalid
     */
    private static CommandExecutor parseDelete(String input) throws RonaldoException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new InvalidTaskNumberException();
        }
        int number = Integer.parseInt(parts[1]) - 1;
        return new DeleteExecutor(number);
    }

    /**
     * Parses a find command with a keyword.
     *
     * @param input the raw user input
     * @return a {@link FindExecutor} for the given keyword
     * @throws RonaldoException if the keyword is missing or empty
     */
    private static CommandExecutor parseFind(String input) throws RonaldoException {
        if (input.length() <= 5) {
            throw new EmptyStringException();
        }

        String keyword = input.substring(5).trim();
        if (keyword.isEmpty()) {
            throw new EmptyStringException();
        }

        return new FindExecutor(keyword);
    }

    /**
     * A simplified parser that categorizes the command type without arguments.
     *
     * @param input the raw user input
     * @return the corresponding {@link Command} enum value,
     *         or {@link Command#INVALID} if the input does not match a known command
     */
    public static Command parse2(String input) {
        if (input.equals("bye")) {
            return Command.BYE;
        } else if (input.equals("list")) {
            return Command.LIST;
        } else if (input.startsWith("mark ")) {
            return Command.MARK;
        } else if (input.startsWith("unmark ")) {
            return Command.UNMARK;
        } else if (input.startsWith("deadline")) {
            return Command.DEADLINE;
        } else if (input.startsWith("event")) {
            return Command.EVENT;
        } else if (input.startsWith("todo")) {
            return Command.TODO;
        } else if (input.startsWith("delete")) {
            return Command.DELETE;
        } else if (input.startsWith("find")) {
            return Command.FIND;
        } else {
            return Command.INVALID;
        }
    }
}
