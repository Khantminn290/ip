package ronaldo.command;

/**
 * Enum representing all possible commands that the user can input
 * in the Ronaldo application.
 */
public enum Command {
    /** List all tasks. */
    LIST,

    /** Mark a task as done. */
    MARK,

    /** Unmark a task as not done. */
    UNMARK,

    /** Add a deadline task. */
    DEADLINE,

    /** Add an event task. */
    EVENT,

    /** Add a todo task. */
    TODO,

    /** Delete a task. */
    DELETE,

    /** Exit the application. */
    BYE,

    /** Find tasks by keyword. */
    FIND,

    /** Invalid or unrecognized command. */
    INVALID;
}
