import java.util.Scanner;

public class Ronaldo {
    private TaskList taskList;
    private Scanner scanner;
    private Storage storage;
    private Ui ui;

    public Ronaldo() {
        this.storage = new Storage();
        this.scanner = new Scanner(System.in);
        this.taskList = new TaskList(storage.load());
        this.ui = new Ui();
        ui.showGreeting();
    }

    public void readInput() {
        String input = "";
        while (!input.equals("bye")) {
            try {
                input = scanner.nextLine();
                Command command = Parser.parse(input);

                switch (command) {
                case BYE:
                    this.ui.showFarewell();
                    return;

                case LIST:
                    this.ui.showTaskList(this.taskList.listTasks());
                    break;

                case MARK: {
                    int number = Integer.parseInt(input.split(" ")[1]) - 1;
                    taskList.markTask(number);
                    ui.showMarkedTask(taskList.getTask(number));
                    break;
                }

                case UNMARK: {
                    int number = Integer.parseInt(input.split(" ")[1]) - 1;
                    taskList.unmarkTask(number);
                    ui.showUnmarkedTask(taskList.getTask(number));
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
                    ui.showAddTask(deadline, taskList.size());
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
                    ui.showAddTask(event, taskList.size());
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
                    ui.showAddTask(toDo, taskList.size());
                    break;
                }

                case DELETE: {
                    int number = Integer.parseInt(input.split(" ")[1]) - 1;
                    Task deletedTask = taskList.deleteTask(number);
                    ui.showDeleteTask(deletedTask, taskList.size());
                    storage.deleteTask(number);
                    break;
                }

                case INVALID:
                default:
                    throw new InvalidInputException();
                }

            } catch (RonaldoException r) {
                ui.showError(r.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Ronaldo ronaldo = new Ronaldo();
        ronaldo.readInput();
    }
}
