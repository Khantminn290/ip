package ronaldo.parser;

import ronaldo.command.Command;

/**
 * Parses raw user input and translates it into a {@link Command}.
 * <p>
 * This class provides a single static method to determine which
 * command the user intends based on the input string.
 */
public class Parser {

    /**
     * Parses a raw user input string and returns the corresponding {@link Command}.
     *
     * @param input The raw string entered by the user.
     * @return A {@link Command} representing the action indicated by the input.
     *         Returns {@link Command#INVALID} if the input does not match any known commands.
     */
    public static Command parse(String input) {
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