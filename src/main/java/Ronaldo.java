import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Ronaldo {

    ArrayList<Task> list = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    private String greeting = " Hello! I'm Ronaldo\n"
            + " What can I do for you? Siuu!";

    private String bye = " Bye. Hope to see you again soon!";

    public static String encase(String s) {
        return "____________________________________________________________\n"
                + String.format("%s\n", s)
                + "____________________________________________________________\n";
    }

    public void greet() {
        System.out.println(encase(this.greeting));
    }

    public void signOff() {
        System.out.println(encase(this.bye));
    }


    public void printtAddedTask(Task task) {
        System.out.println(encase("Got it. I've added this task:\n  " + task + "\n"
                + String.format("Now you have %d tasks in the list", this.list.size())));
    }

    // function to write to data file in hard disk
    public void writeToHardDisk(String input) {
        try {
            // Ensure the 'data' folder exists
            Path folder = Path.of("./src/main/java/data");
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);  // create folder if missing
            }

            // Write the task to ronaldo.txt in append mode
            Path file = folder.resolve("ronaldo.txt"); // ./src/main/java/data/ronaldo.txt
            if (!Files.exists(file)) {
                Files.createFile(file);  // create file if missing
            }

            FileWriter writer = new FileWriter(file.toFile(), true);
            writer.write(String.format(input) + System.lineSeparator());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDataFromHardDisk(int number) {
        try {
            Path file = Path.of("./src/main/java/data/ronaldo.txt");
            // Read all lines from the file
            List<String> lines = Files.readAllLines(file);
            List<String> updatedLines = new ArrayList<>();

            // Keep all lines except the one to delete
            for (int i = 0; i < lines.size(); i++) {
                if (i != number) {
                    updatedLines.add(lines.get(i));
                }
            }

            // Overwrite the file with the updated list
            Files.write(file, updatedLines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printtDeletedTask(Task task) {
        System.out.println(encase("Noted. I've removed this task:\n  " + task + "\n"
                + String.format("Now you have %d tasks in the list", this.list.size())));
    }
    // reads user input
    public void readInput() {
        String input = "";
        while (!input.equals("bye")) {
            try {
                input = scanner.nextLine();
                Command command = parseCommand(input);

                switch (command) {
                case BYE:
                    this.signOff();
                    return;

                case LIST:
                    this.printTask();
                    break;

                case MARK: {
                    String[] parts = input.split(" ");
                    int number = Integer.parseInt(parts[1]);
                    Task markedTask = this.list.get(number - 1);
                    markedTask.markAsDone();
                    break;
                }

                case UNMARK: {
                    String[] parts = input.split(" ");
                    int number = Integer.parseInt(parts[1]);
                    Task unmarkTask = this.list.get(number - 1);
                    unmarkTask.unmark();
                    break;
                }

                case DEADLINE: {
                    String[] parts = input.split(" /by ");
                    String[] split = parts[0].split(" ", 2);
                    String description = split[1];
                    if (description.isBlank()) {
                        throw new EmptyStringException();
                    }
                    String by = parts[1];
                    Deadline deadline = new Deadline(description, by);
                    this.list.add(deadline);
                    String written_format = String.format("D | %s | %s | %s", deadline.isDone, description, by );
                    writeToHardDisk(written_format);
                    printtAddedTask(deadline);
                    break;
                }

                case EVENT: {
                    String[] parts = input.split("/from|/to");

                    String description = parts[0].replaceFirst("event\\s+", "").trim();
                    if (description.isBlank()) {
                        throw new EmptyStringException();
                    }

                    String from = parts[1].trim();
                    String to = parts[2].trim();

                    Event event = new Event(description, from, to);
                    this.list.add(event);
                    String written_format = String.format("E | %s | %s | %s-%s", event.isDone, description, from, to);
                    writeToHardDisk(written_format);
                    printtAddedTask(event);
                    break;
                }

                case TODO: {
                    String[] parts = input.split(" ", 2);
                    String description = parts[1];
                    if (description.isBlank()) {
                        throw new EmptyStringException();
                    }
                    ToDos toDos = new ToDos(description);
                    this.list.add(toDos);
                    String written_format = String.format("T | %s | %s", toDos.isDone, description);
                    writeToHardDisk(written_format);
                    printtAddedTask(toDos);
                    break;
                }

                case DELETE: {
                    String[] parts = input.split(" ", 2);
                    int number = Integer.parseInt(parts[1]) - 1;
                    Task deletedTask = this.list.get(number);
                    this.list.remove(number);
                    printtDeletedTask(deletedTask);
                    deleteDataFromHardDisk(number);
                    break;
                }

                case INVALID:
                default:
                    throw new InvalidInputException();
                }

            } catch (RonaldoException r) {
                System.out.println(r.getMessage());
            }
        }
    }


    public void printTask() {
        int size = this.list.size();
        StringBuilder tasks = new StringBuilder();
        for (int i = 0; i < size; i++) {
            tasks.append(String.format("%d. %s", i + 1, this.list.get(i)));
            if (i < size - 1) {
                tasks.append("\n");
            }
        }
        System.out.println(encase("Here are the tasks in your list: \n" + tasks));
    }

    public static Command parseCommand(String input) {
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
        } else {
            return Command.INVALID;
        }
    }
    public void echo() {
        String input =  "";
        while(!input.equals("bye")) {
            input = scanner.nextLine();
            if (input.equals("bye")) {
                this.signOff();
                break;
            } else {
                System.out.println(encase(input));
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        Ronaldo ronaldo = new Ronaldo();
        ronaldo.greet();
        ronaldo.readInput();
    }
}
