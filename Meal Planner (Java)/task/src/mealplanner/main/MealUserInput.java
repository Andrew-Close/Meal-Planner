package mealplanner.main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is specific to this program. It gets valid user input, but the methods are specific to what this program needs.
 */

public class MealUserInput {
    /**
     * This enum ensures that user input for what operation to do is correct
     */
    private enum Operations {
        ADD("add"), SHOW("show"), EXIT("exit");
        private final String operation;

        Operations(String operation) {
            this.operation = operation;
        }

        private String getOperation() {
            return this.operation;
        }
    }

    /**
     * This enum ensures that user input for a meal category is correct
     */
    public enum Categories {
        BREAKFAST("breakfast"), LUNCH("lunch"), DINNER("dinner");
        private final String category;

        Categories(String category) {
            this.category = category;
        }

        private String getCategory() {
            return this.category;
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Gets a valid operation from user input. Will keep looping until the user inputs a valid operation
     * @return = the operation
     */
    static String getValidOperation() {
        while (true) {
            String operation = scanner.nextLine();
            for (Operations enumOperation : Operations.values()) {
                if (enumOperation.getOperation().equals(operation)) {
                    return operation;
                }
            }
            System.out.println("What would you like to do (add, show, exit)?");
        }
    }

    /**
     * Gets a valid meal category from user input. Will keep looping until the user inputs a valid category
     * @return = the category
     */
    static String getValidCategory() {
        while (true) {
            String category = scanner.nextLine();
            for (Categories enumCategory : Categories.values()) {
                if (enumCategory.getCategory().equals(category)) {
                    return category;
                }
            }
            System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
        }
    }

    /**
     * Gets a valid array of ingredients from user input. Will keep looping until the user inputs a valid ingredients array
     * @return = the array of ingredients
     */
    static String[] getValidIngredients() {
        // This regex matches anything other than letters and whitespace. So if it matches anything in the input, the input is wrong
        String lettersRegex = "[^a-zA-Z\\s]";
        // This regex matches anything other than whitespace. So if it matches nothing in the input, the input is wrong
        String emptyRegex = "\\S";
        // Pattern for the letters regex
        Pattern lettersPattern = Pattern.compile(lettersRegex);
        // Pattern for the empty regex
        Pattern emptyPattern = Pattern.compile(emptyRegex);
        whileloop:
        while (true) {
            String userInputString = scanner.nextLine();
            // Checks the corner case of there being a comma at the end of the input
            if (userInputString.length() >= 2) {
                if (userInputString.substring(userInputString.length() - 2).matches(",|, ")) {
                    System.out.println("Wrong format. Use letters only!");
                    continue;
                }
            }
            String[] userInput = userInputString.split(", ");
            for (String ingredient : userInput) {
                Matcher lettersMatcher = lettersPattern.matcher(ingredient);
                Matcher emptyMatcher = emptyPattern.matcher(ingredient);
                // Checks each element in the split array rather than the entire inputted string
                if (lettersMatcher.find() || !emptyMatcher.find()) {
                    System.out.println("Wrong format. Use letters only!");
                    continue whileloop;
                }
            }
            return userInput;
        }
    }
}
