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

    // addTask contains mark and unmark feature from user input
    public void addTask() {
        String input = "";
        while(!input.equals("bye")) {
            input = scanner.nextLine();
            if (input.equals("bye")) {
                this.signOff();
                break;
            } else if (input.equals("list")) {
                this.printTask();
            } else if (input.contains("mark") ) {
                if (input.contains("unmark")) {
                    String[] parts = input.split(" ");
                    String numbrStr = parts[1];
                    int number = Integer.parseInt(numbrStr);
                    Task unmarkTask = this.list.get(number - 1);
                    unmarkTask.unmark();
                    System.out.println(encase("OK, I've marked this task as not done yet:\n" + unmarkTask));
                } else {
                    String[] parts = input.split(" ");
                    String numbrStr = parts[1];
                    int number = Integer.parseInt(numbrStr);
                    Task markedTask = this.list.get(number - 1);
                    markedTask.markAsDone();
                    System.out.println(encase("Nice! I've marked this task as done:\n " + markedTask));
                }
            } else {
                System.out.println(encase("added: " + input));
                this.list.add(new Task(input));
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
        ronaldo.addTask();

    }
}
