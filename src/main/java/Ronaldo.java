import java.util.Scanner;
import java.util.ArrayList;

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
    // reads user input
    public void readInput() {
        String input = "";
        while (!input.equals("bye")) {
            try {
                input = scanner.nextLine();

                if (input.equals("bye")) {
                    this.signOff();
                    break;
                } else if (input.equals("list")) {
                    this.printTask();
                } else if (input.contains("mark")) {
                    if (input.contains("unmark")) {
                        String[] parts = input.split(" ");
                        int number = Integer.parseInt(parts[1]);
                        Task unmarkTask = this.list.get(number - 1);
                        unmarkTask.unmark();
                    } else {
                        String[] parts = input.split(" ");
                        int number = Integer.parseInt(parts[1]);
                        Task markedTask = this.list.get(number - 1);
                        markedTask.markAsDone();
                    }
                } else if (input.contains("deadline")) {
                    String[] parts = input.split("/by");
                    String[] split = parts[0].split(" ", 2);
                    String description = split[1];
                    if (description.isBlank()) {
                        throw new EmptyStringException();
                    }
                    String by = parts[1];
                    Deadline deadline = new Deadline(description, by);
                    this.list.add(deadline);
                    printtAddedTask(deadline);
                } else if (input.contains("event")) {
                    String[] parts = input.split("/");
                    String[] split = parts[0].split(" ", 2);
                    String description = split[1];
                    if (description.isBlank()) {
                        throw new EmptyStringException();
                    }
                    String from = parts[1];
                    String to = parts[2];
                    Event event = new Event(description, from, to);
                    this.list.add(event);
                    printtAddedTask(event);
                } else if (input.contains("todo")) {
                    String[] parts = input.split(" ", 2);
                    String description = parts[1];
                    if (description.isBlank()) {
                        throw new EmptyStringException();
                    }
                    ToDos toDos = new ToDos(description);
                    this.list.add(toDos);
                    printtAddedTask(toDos);
                } else {
                    throw new InvalidInputException();
                }

            } catch (RonaldoException r) {
                System.out.println(r.getMessage());
            }
        }
        scanner.close();
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
