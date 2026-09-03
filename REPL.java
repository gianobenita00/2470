import java.util.Scanner;
import java.util.Random; 

public class REPL {
    public static void main(String[] args) {

        System.out.println("Welcome to my REPL App!");
        System.out.println("Type 'help' to show available commands!");

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.print("\n> ");
            String command = sc.nextLine().trim().toLowerCase();

            switch(command) {
                case "help":
                    System.out.println("\nAvailable commands:");
                    System.out.println(" add");
                    System.out.println(" subtract");
                    System.out.println(" multiply ");
                    System.out.println(" random");
                    System.out.println(" reverse");
                    System.out.println(" quit");
                    break;

                case "add":
                case "subtract":
                case "multiply":
                case "divide":
                    doMath(sc, command);
                    break;

                case "reverse":
                    System.out.print("Enter text: ");
                    String text = sc.nextLine();
                    System.out.println(reverseString(text));
                    break;

                case "random":
                    System.out.print("Minimum: ");
                    int min = Integer.parseInt(sc.nextLine());
                    System.out.print("Maximum: ");
                    int max = Integer.parseInt(sc.nextLine());
                    Random rand = new Random();
                    int result = rand.nextInt(max - min + 1) + min;
                    System.out.println("Random number: " + result);
                    break;
                
                    case "quit":
                        System.out.println("Goodbye!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Unkown command. Type 'help' for available commands.");
            }
        }
    }

    private static void doMath(Scanner scanner, String command) {
        
        System.out.print("First number: ");
        double first = Double.parseDouble(scanner.nextLine());
        System.out.print("Second number: ");
        double second = Double.parseDouble(scanner.nextLine());

        double result = 0;

        switch(command) {
            case "add":
                result = first + second;
                break;
            case "subtract":
                result = first - second;
                break;
            case "multiply":
                result = first * second;
                break;
            case "divide":
                if (second == 0) {
                    System.out.println("Error: cannot divide by zero.");
                    return;
                }
                result = first / second;
                break;
        }

        System.out.println("Result: " + result);
    }

    private static String reverseString(String input) {

        return new StringBuilder(input).reverse().toString();
    }
}
