package ronaldo.storage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

import ronaldo.exceptions.RonaldoException;
import ronaldo.task.Deadline;
import ronaldo.task.Event;
import ronaldo.task.Priority;
import ronaldo.task.Task;
import ronaldo.task.ToDo;

/**
 * Handles persistent storage of tasks for the Ronaldo task manager.
 * Creates and manages the storage file, and provides methods
 * to write, delete, and load tasks.
 */
public class Storage {

    /** Path to the folder containing the storage file. */
    protected final Path folder;

    /** Path to the storage file where tasks are saved. */
    protected final Path file;

    /**
     * Constructs a {@code Storage} object.
     * Ensures that the storage folder and file are created if they do not exist.
     */
    public Storage() {
        this.folder = Path.of("./data");
        this.file = folder.resolve("ronaldo.txt");

        try {
            if (!Files.exists(folder)) {
                System.out.println("folder not found");
                Files.createDirectories(folder);
            }
            if (!Files.exists(file)) {
                System.out.println("file not found");
                Files.createFile(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a task representation to the storage file.
     *
     * @param line the string representation of the task to be stored.
     */
    public void writeTask(String line) throws RonaldoException {
        try (FileWriter writer = new FileWriter(this.file.toFile(), true)) {
            writer.write(line + System.lineSeparator());
        } catch (IOException e) {
            throw new RonaldoException("Error writing task to files.");
        }
    }

    /**
     * Deletes a task from the storage file by its index.
     *
     * @param index the zero-based index of the task to delete.
     */
    public void deleteTask(int index) throws RonaldoException {
        try {
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(file));
            lines.remove(index);
            Files.write(file, lines);
        } catch (IOException e) {
            throw new RonaldoException("Error deleting task from files.");
        }
    }

    /**
     * Marks a task as done in the storage file by its index.
     * Updates the boolean value in the second column from false to true.
     *
     * @param index the zero-based index of the task to mark as done.
     * @throws RonaldoException if the index is invalid or an I/O error occurs.
     */
    public void markTask(int index) throws RonaldoException {
        try {
            // Read all lines from the file
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(file));

            // Check if index is valid
            if (index < 0 || index >= lines.size()) {
                throw new RonaldoException("Invalid task index: " + index);
            }

            // Get the specific line to modify
            String line = lines.get(index);
            String[] parts = line.split(" \\| ");

            // Check if the line has the expected format
            if (parts.length < 3) {
                throw new RonaldoException("Invalid task format at index: " + index);
            }

            // Update the status from false to true
            parts[1] = "true";

            // Reconstruct the line
            String updatedLine = String.join(" | ", parts);

            // Replace the old line with the updated one
            lines.set(index, updatedLine);

            // Write all lines back to the file
            Files.write(file, lines);

        } catch (IOException e) {
            throw new RonaldoException("Error marking task in file: " + e.getMessage());
        }
    }

    /**
     * Unmarks a task (sets it as not done) in the storage file by its index.
     * Updates the boolean value in the second column from true to false.
     *
     * @param index the zero-based index of the task to unmark.
     * @throws RonaldoException if the index is invalid or an I/O error occurs.
     */
    public void unmarkTask(int index) throws RonaldoException {
        try {
            // Read all lines from the file
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(file));

            // Check if index is valid
            if (index < 0 || index >= lines.size()) {
                throw new RonaldoException("Invalid task index: " + index);
            }

            // Get the specific line to modify
            String line = lines.get(index);
            String[] parts = line.split(" \\| ");

            // Check if the line has the expected format
            if (parts.length < 3) {
                throw new RonaldoException("Invalid task format at index: " + index);
            }

            // Update the status from true to false
            parts[1] = "false";

            // Reconstruct the line
            String updatedLine = String.join(" | ", parts);

            // Replace the old line with the updated one
            lines.set(index, updatedLine);

            // Write all lines back to the file
            Files.write(file, lines);

        } catch (IOException e) {
            throw new RonaldoException("Error unmarking task in file: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the storage file into memory.
     * Reconstructs task objects (ToDos, Deadlines, Events) from their stored string representations.
     *
     * @return an Arraylist of tasks loaded from the file. Returns an empty list if the file is empty.
     */
    public ArrayList<Task> load() {
        try {
            return Files.readAllLines(file).stream()
                    .map(line -> {
                        if (line.trim().isEmpty()) {
                            return null; // skip blank lines
                        }
                        String[] parts = line.split(" \\| ");
                        String type = parts[0];
                        boolean isDone = Boolean.parseBoolean(parts[1]);
                        Task task = null;

                        try {
                            Priority priority = Priority.fromString(parts[2]);

                            switch (type) {
                            case "T":
                                task = new ToDo(parts[3]);
                                task.setPriority(priority);
                                break;
                            case "D":
                                task = new Deadline(parts[3], parts[4]);
                                task.setPriority(priority);
                                break;
                            case "E":
                                String[] time = parts[4].split(" - ");
                                task = new Event(parts[3], time[0], time[1]);
                                task.setPriority(priority);
                                break;
                            default:
                                System.out.println("Unknown task type: " + type);
                            }

                            if (task != null && isDone) {
                                task.markAsDone();
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid priority in saved task: " + parts[2]);
                        }

                        return task;
                    })
                    .filter(task -> task != null) // drop unknown types
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
