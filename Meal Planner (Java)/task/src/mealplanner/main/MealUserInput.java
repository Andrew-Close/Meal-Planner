package mealplanner.main;

import java.util.Scanner;

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

    private static Scanner scanner = new Scanner(System.in);

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
}
