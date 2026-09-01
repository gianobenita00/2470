public class Challenge {
    public static void main(String[] args) {
        
        //Challenge Operators
        int a = 20;
        int b = 10;

        System.out.println("Addition: "  + (a + b));
        System.out.println("Subtraction: " + (a - b));
        System.out.println("Multiplication: " + (a*b));
        System.out.println("Division: " + (a/b));

        if (a > b) {
            System.out.println("Is a greater that b? true");
        }

        if ((a > b) && (b > 0)) {
            System.out.println("Is a > b and b > 0? true");

        }

        //Challenge Control Flow
        int score = 75; 
        char grade = 'B';

        if (score >= 50) {
            System.out.println("Passed");
        } else {
            System.out.println("Failed");
        }

        if (score >= 90){
            grade = 'A';
        } else if (score >= 75){
            grade = 'B';
        } else if (score >= 64){
            grade = 'C';
        } else {
            grade = 'D';
        }

        System.out.println("Grade: " + grade);

        //Challenge Loops 

        //For
        System.out.print("For Loop: ");

        for (int i = 1; i <= 5 ; i++) {
            System.out.print(i);
            if (i < 5) {
                System.out.print(" ");
            }
        }
        System.out.println();

        //While
        System.out.print("While Loop: ");

        int i = 1;

        while (i <= 5) {
            System.out.print(i);

            if (i < 5) {
                System.out.print(" ");
            }
            i++;
        }
        System.out.println();

        //Do While
        System.out.print("Do While Loop: ");

        int j = 1;

        do {
            System.out.print(j);

            if (j < 5) {
                System.out.print(" ");
            }

            j++;

        } while (j <= 5);

        System.out.println();
    }
}