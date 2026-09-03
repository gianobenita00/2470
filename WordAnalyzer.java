import java.util.Scanner;


public class WordAnalyzer {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a word: ");
        String input = sc.nextLine();

        int vowels     = 0;
        int consonants = 0;
        int digits     = 0;
        int spaces     = 0;

        String vowelLetters = "aeiouAEIOU";

        for (char c : input.toCharArray()) {
            if (c == ' ') {
                spaces++;
            } else if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)){
                if (vowelLetters.indexOf(c) != -1){
                    vowels++;
                } else {
                    consonants++;
                }

            }

        }

        System.out.println("Characters: " + input.length());
        System.out.println("Vowels:     " + vowels        );
        System.out.println("Consonants: " + consonants    );
        System.out.println("Digits:     " + digits        );
        System.out.println("Spaces:     " + spaces        );

        sc.close();
    }
}
