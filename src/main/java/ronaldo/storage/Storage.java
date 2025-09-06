package ronaldo.storage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

import ronaldo.task.Deadline;
import ronaldo.task.Event;
import ronaldo.task.Task;
import ronaldo.task.ToDos;
import ronaldo.exceptions.RonaldoException;

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
     * Loads tasks from the storage file into memory.
     * Reconstructs task objects (ToDos, Deadlines, Events) from their stored string representations.
     *
     * @return a list of tasks loaded from the file. Returns an empty list if the file is empty or an error occurs.
     */
    public ArrayList<Task> load() {
        try {
            return Files.readAllLines(file).stream()
                    .map(line -> {
                        String[] parts = line.split(" \\| ");
                        String type = parts[0];
                        boolean isDone = Boolean.parseBoolean(parts[1]);
                        Task task = null;

                        switch (type) {
                        case "Todo":
                            task = new ToDos(parts[2]);
                            break;
                        case "Deadline":
                            task = new Deadline(parts[2], parts[3]);
                            break;
                        case "Event":
                            String[] time = parts[3].split("-");
                            task = new Event(parts[2], time[0], time[1]);
                            break;
                        default:
                            System.out.println("Unknown task type: " + type);
                        }

                        if (task != null && isDone) {
                            task.markAsDone();
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

