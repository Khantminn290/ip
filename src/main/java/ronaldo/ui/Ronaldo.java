package ronaldo.ui;

import java.util.Scanner;

import ronaldo.command.CommandExecutor;
import ronaldo.exceptions.RonaldoException;
import ronaldo.parser.Parser;
import ronaldo.storage.Storage;
import ronaldo.task.TaskList;

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
        try {
            CommandExecutor executor = Parser.parse(input);
            String message = executor.execute(taskList, storage, ui);
            return message;
        } catch (RonaldoException e) {
            return e.getMessage();
        }

    }

    public static void main(String[] args) {
        Ronaldo ronaldo = new Ronaldo();
        ronaldo.readInput();
    }
}

