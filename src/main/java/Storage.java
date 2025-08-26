import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Storage {
    protected final Path folder;
    protected final Path file;

    public Storage() {
        this.folder = Path.of("./src/main/java/data");
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

    public void writeTask(String line) {
        try (FileWriter writer = new FileWriter(this.file.toFile(), true)) {
            writer.write(line + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int index) {
        try {
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(file));
            lines.remove(index);
            Files.write(file, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load tasks from file into an ArrayList<Task>
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(file));
            for (String line : lines) {
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = Boolean.parseBoolean(parts[1]);
                switch (type) {
                case "T":
                    ToDos todo = new ToDos(parts[2]);
                    if (isDone) todo.markAsDone();
                    tasks.add(todo);
                    break;
                case "D":
                    Deadline deadline = new Deadline(parts[2], parts[3]);
                    if (isDone) deadline.markAsDone();
                    tasks.add(deadline);
                    break;
                case "E":
                    String[] time = parts[3].split("-");
                    Event event = new Event(parts[2], time[0], time[1]);
                    if (isDone) event.markAsDone();
                    tasks.add(event);
                    break;
                default:
                    System.out.println("Unknown task type: " + type);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
