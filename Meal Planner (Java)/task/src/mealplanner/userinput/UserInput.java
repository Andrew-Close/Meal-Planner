package mealplanner.userinput;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInput {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Gets a string consisting of just letters and whitespace. Will keep looping until the user inputs a correct string
     * @return = the string, as long as it is valid
     */
    public static String getAlphabeticalString(String errorMessage) {
        // This regex matches anything other than letters and whitespace. So if it matches anything in the input, the input is wrong
        String lettersRegex = "[^a-zA-Z\\s]";
        // This regex matches anything other than whitespace. So if it matches nothing in the input, the input is wrong
        String emptyRegex = "\\S";
        // Pattern for the letters regex
        Pattern lettersPattern = Pattern.compile(lettersRegex);
        // Pattern for the empty regex
        Pattern emptyPattern = Pattern.compile(emptyRegex);
        while (true) {
            String userInput = scanner.nextLine();
            Matcher lettersMatcher = lettersPattern.matcher(userInput);
            Matcher emptyMatcher = emptyPattern.matcher(userInput);
            if (lettersMatcher.find() || !emptyMatcher.find()) {
                System.out.println(errorMessage);
                continue;
            }
            return userInput;
        }
    }
}
