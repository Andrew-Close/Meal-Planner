package mealplanner.main;

import mealplanner.meal.Meal;
import mealplanner.meal.datamanager.DataManager;
import mealplanner.userinput.UserInput;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
  // Contains all saved meals
  static final ArrayList<Meal> savedMeals = new ArrayList<>();

  public static void main(String[] args) throws SQLException {
    System.out.println(DataManager.getMessage());
    // The user input loop
    inputLoop();
  }

  /**
   * This is the loop for getting user input and performing actions
   */
  private static void inputLoop() {
    // The while loop uses this boolean as the condition, so when this boolean is set to false, the loop terminates
    boolean shouldContinue = true;
    while (shouldContinue) {
      String message = DataManager.getMessage();
      System.out.println("What would you like to do (add, show, exit)?");
      String operation = MealUserInput.getValidOperation();
      switch (operation) {
        case "add":
          addMeal();
          break;
        case "show":
          if (message.isEmpty()) {
            System.out.println("No meals saved. Add a meal first.");
          } else {
            System.out.println(message);
          }
          /*
          showMeals();
           */
          break;
        case "exit":
          shouldContinue = false;
          System.out.println("Bye!");
      }
    }
  }

  /**
   * Adds a new meal to the savedMeals ArrayList. Uses user input classes to get valid input
   */
  private static void addMeal() {
    System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
    String category = MealUserInput.getValidCategory();
    System.out.println("Input the meal's name:");
    String name = UserInput.getAlphabeticalString("Wrong format. Use letters only!");
    System.out.println("Input the ingredients:");
    String[] ingredients = MealUserInput.getValidIngredients();
    String mealID = Integer.toString(DataManager.getNextMealID());
    DataManager.insertInto(DataManager.Tables.MEALS, new String[]{category, name, mealID});
    for (String ingredient : ingredients) {
      DataManager.insertInto(DataManager.Tables.INGREDIENTS, new String[]{ingredient, Integer.toString(DataManager.getNextIngredientID()), mealID});
    }
    System.out.println("The meal has been added!");
  }

  /**
   * Shows all the saves meals in the savedMeals ArrayList
   */
  private static void showMeals() {
    // Will tell the user to add a meal first if no meal has been added yet
    if (savedMeals.isEmpty()) {
      System.out.println("No meals saved. Add a meal first.");
    } else {
      for (Meal meal : savedMeals) {
        System.out.println();
        System.out.printf("Category: %s%n", meal.category());
        System.out.printf("Name: %s%n", meal.name());
        System.out.println("Ingredients:");
        for (String ingredient : meal.ingredients()) {
          System.out.println(ingredient);
        }
      }
      System.out.println();
    }
  }
}