package ronaldo.parser;

import java.util.ArrayList;

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
import ronaldo.exceptions.RonaldoException;
import ronaldo.task.Task;

public class Parser {

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

    private static CommandExecutor parseMark(String input, boolean isMark) throws RonaldoException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new InvalidTaskNumberException();
        }
        int number = Integer.parseInt(parts[1]) - 1;
        return new MarkExecutor(number, isMark);
    }

    private static CommandExecutor parseDeadline(String input) throws RonaldoException {
        String[] parts = input.split(" /by ");
        if (parts.length != 2) {
            throw new InvalidDeadlineTaskException();
        }

        String description = parts[0].replaceFirst("deadline\\s+", "").trim();
        if (description.isBlank()) {
            throw new EmptyStringException();
        }

        String by = parts[1].trim();
        if (by.isBlank()) {
            throw new EmptyStringException();
        }

        // Validate date format
        try {
            java.time.format.DateTimeFormatter formatter =
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            java.time.LocalDateTime.parse(by, formatter);
        } catch (java.time.format.DateTimeParseException e) {
            throw new InvalidDateFormatException();
        }

        return new DeadlineExecutor(description, by);
    }

    private static CommandExecutor parseEvent(String input) throws RonaldoException {
        String[] parts = input.split("/from|/to");
        if (parts.length != 3) {
            throw new InvalidEventTaskException();
        }

        String description = parts[0].replaceFirst("event\\s+", "").trim();
        if (description.isBlank()) {
            throw new EmptyStringException();
        }

        String from = parts[1].trim();
        String to = parts[2].trim();
        if (from.isBlank() || to.isBlank()) {
            throw new EmptyStringException();
        }

        return new EventExecutor(description, from, to);
    }

    private static CommandExecutor parseTodo(String input) throws RonaldoException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            throw new EmptyStringException();
        }

        String description = parts[1].trim();
        if (description.isBlank()) {
            throw new EmptyStringException();
        }

        return new TodoExecutor(description);
    }

    private static CommandExecutor parseDelete(String input) throws RonaldoException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new InvalidTaskNumberException();
        }
        int number = Integer.parseInt(parts[1]) - 1;
        return new DeleteExecutor(number);
    }

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