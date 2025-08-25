import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
        try {
            FileWriter writer = new FileWriter(this.file.toFile(), true);
            writer.write(line + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int index) {
        try {
            List<String> lines = Files.readAllLines(file);
            List<String> updated = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                if (i != index) {
                    updated.add(lines.get(i));
                }
            }
            Files.write(file, updated);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}