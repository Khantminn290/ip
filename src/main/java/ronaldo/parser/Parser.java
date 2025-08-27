package ronaldo.parser;

import ronaldo.ui.Command;

public class Parser {
    /**
     * Parses raw user input and returns the corresponding Command.
     *
     * @param input The raw string entered by the user.
     * @return A Command enum representing the input.
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
        }
        else {
            return Command.INVALID;
        }
    }
}
