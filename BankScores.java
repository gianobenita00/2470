import java.util.Scanner;

public class BankScores {
    public static void main(String[] args){

        //Test Score Challenge

        Scanner input = new Scanner(System.in);

        int[] scores = new int[5];

        for (int i = 0 ; i < scores.length ; i++) {
            System.out.print("Enter score " + (i + 1) + ": ");
            scores[i] = input.nextInt();
        }

        int total = 0; 
        
        int highest = scores[0];
        int lowest = scores[0];

        for (int score : scores) {

            total = total + score;

            if (score > highest) {
                highest = score;
            }

            if (score < lowest) {
                lowest = score;
            }

        }

        double average = (double) total / scores.length;

        System.out.println("\nTotal:   " + total);
        System.out.println("Average: " + average);
        System.out.println("Highest: " + highest);
        System.out.println("Loweset: " + lowest);
        System.out.println("\nLetter Grades: ");

        for (int score : scores) {

            char grade;

            if (score >= 90) {
                grade = 'A';
            }

            else if (score >= 80) {
                grade = 'B';
            }

            else if (score >= 70) {
                grade = 'C';
            }

            else if (score >= 60) {
                grade = 'D';
            } else {
                grade = 'F';
            }

            System.out.println(score + " - " + grade);

        }


        //Bank Challenge
        double balance = 0;
        int choice = 0;

        while (choice != 4) {

            System.out.println("\n1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");

            System.out.print("\nSelect a command: ");
            choice = input.nextInt();

            if (choice == 1) {
                System.out.println("Your balance is: $" + balance);
            } 
            
            else if (choice == 2) {

                System.out.print("Enter deposit amount: $");
                double deposit = input.nextDouble();

                if (deposit < 0) {
                    System.out.println("Deposit cannot be negative!");
                } else {
                    balance = balance + deposit;
                    System.out.println("Deposit successful.");
                }

            } 
            
            else if (choice == 3) {

                System.out.print("Enter withdrawal amount: $");
                double withdraw = input.nextDouble();

                if (withdraw < 0) {
                    System.out.println("Withdrawal cannot be negative!");

                } else if (withdraw > balance) {
                    System.out.println("Insufficient funds.");

                } else {
                    balance = balance - withdraw;
                    System.out.println("Withdrawal successful.");
                }
            } 
            
            else if (choice == 4) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid command.");
            }
        }

        input.close();

    }
}