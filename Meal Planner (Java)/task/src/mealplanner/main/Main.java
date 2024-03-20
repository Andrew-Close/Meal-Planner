package mealplanner.main;

import mealplanner.meal.datamanager.DataManager;
import mealplanner.userinput.UserInput;

public class Main {
  public static void main(String[] args) {
    // Since the test gets rid of all tables at the start of execution, I need to initialize the tables if they don't already exist
    DataManager.initializeTables();
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
      //String message = DataManager.getMessage();
      // Caches the message
      System.out.println("What would you like to do (add, show, exit)?");
      String operation = MealUserInput.getValidOperation();
      switch (operation) {
        case "add":
          addMeal();
          break;
        case "show":
          showMeals();
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
   * Shows all meals in the category specified by the user
   */
  private static void showMeals() {
    System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
    System.out.println(DataManager.getMessage(MealUserInput.getValidCategory()));
  }
}