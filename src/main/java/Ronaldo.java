import java.util.Scanner;

public class Ronaldo {
    private TaskList taskList;
    private Scanner scanner;
    private Storage storage;

    public Ronaldo() {
        this.storage = new Storage();
        this.scanner = new Scanner(System.in);
        this.taskList = new TaskList();
    }

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

    public void printAddedTask(Task task) {
        System.out.println(encase("Got it. I've added this task:\n  " + task + "\n"
                + String.format("Now you have %d tasks in the list", this.taskList.size())));
    }

    public void printDeletedTask(Task task) {
        System.out.println(encase("Noted. I've removed this task:\n  " + task + "\n"
                + String.format("Now you have %d tasks in the list", this.taskList.size())));
    }

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
                    this.printTasks();
                    break;

                case MARK: {
                    int number = Integer.parseInt(input.split(" ")[1]) - 1;
                    taskList.markTask(number);
                    break;
                }

                case UNMARK: {
                    int number = Integer.parseInt(input.split(" ")[1]) - 1;
                    taskList.unmarkTask(number);
                    break;
                }

                case DEADLINE: {
                    String[] parts = input.split(" /by ");
                    String description = parts[0].replaceFirst("deadline\\s+", "").trim();
                    if (description.isBlank()) {
                        throw new EmptyStringException();
                    }
                    String by = parts[1].trim();
                    Deadline deadline = new Deadline(description, by);
                    taskList.addTask(deadline);
                    String written_format = String.format("D | %s | %s | %s", deadline.isDone, description, by);
                    storage.writeTask(written_format);
                    printAddedTask(deadline);
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
                    taskList.addTask(event);
                    String written_format = String.format("E | %s | %s | %s-%s", event.isDone, description, from, to);
                    storage.writeTask(written_format);
                    printAddedTask(event);
                    break;
                }

                case TODO: {
                    String description = input.split(" ", 2)[1].trim();
                    if (description.isBlank()) {
                        throw new EmptyStringException();
                    }
                    ToDos toDo = new ToDos(description);
                    taskList.addTask(toDo);
                    String written_format = String.format("T | %s | %s", toDo.isDone, description);
                    storage.writeTask(written_format);
                    printAddedTask(toDo);
                    break;
                }

                case DELETE: {
                    int number = Integer.parseInt(input.split(" ")[1]) - 1;
                    Task deletedTask = taskList.deleteTask(number);
                    printDeletedTask(deletedTask);
                    storage.deleteTask(number);
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

    public void printTasks() {
        System.out.println(encase("Here are the tasks in your list: \n" + taskList.listTasks()));
    }

    public static Command parseCommand(String input) {
        if (input.equals("bye")) return Command.BYE;
        else if (input.equals("list")) return Command.LIST;
        else if (input.startsWith("mark ")) return Command.MARK;
        else if (input.startsWith("unmark ")) return Command.UNMARK;
        else if (input.startsWith("deadline")) return Command.DEADLINE;
        else if (input.startsWith("event")) return Command.EVENT;
        else if (input.startsWith("todo")) return Command.TODO;
        else if (input.startsWith("delete")) return Command.DELETE;
        else return Command.INVALID;
    }

    public static void main(String[] args) {
        Ronaldo ronaldo = new Ronaldo();
        ronaldo.greet();
        ronaldo.readInput();
    }
}
