import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class PasswordValidator {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        System.out.print("Create a password: ");
        String password = sc.nextLine();

        List<String> errors = new ArrayList<>();

        if (password.length() < 8) {
            errors.add("Must be atleast 8 characters.");
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;    
            if (Character.isDigit(c))     hasDigit = true;           
        }

        if (!hasUpper) errors.add("Must contain an uppercase letter");
        if (!hasLower) errors.add("Must contain an lowercase letter");
        if (!hasDigit) errors.add("Must contain a number")           ;

        if (errors.isEmpty()) {
            System.out.println("Password accepted!");
        } else {
            System.out.println("Password not accepted.");

            for (String error : errors) {
                System.out.println("- " + error);
            }
        }

        sc.close();
    }
}
