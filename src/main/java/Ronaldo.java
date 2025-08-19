import java.util.Scanner;

public class Ronaldo {
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

    public void echo() {
        Scanner scanner = new Scanner(System.in);
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
        ronaldo.echo();
    }
}
