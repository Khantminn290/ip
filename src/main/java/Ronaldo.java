import java.util.Scanner;
import java.util.ArrayList;

public class Ronaldo {
    ArrayList<String> list = new ArrayList<>();
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

    public void addTask() {
        String input = "";
        while(!input.equals("bye")) {
            input = scanner.nextLine();
            if (input.equals("bye")) {
                this.signOff();
                break;
            } else if (input.equals("list")) {
                this.printTask();
            } else {
                System.out.println(encase("added: " + input));
                this.list.add(input);
            }
        }
        scanner.close();
    }

    public void printTask() {
        int size = this.list.size();
        for (int i = 0; i < size; i++) {
            System.out.println(encase(String.format("%d. %s", i+1, this.list.get(i))));
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
        ronaldo.addTask();

    }
}
