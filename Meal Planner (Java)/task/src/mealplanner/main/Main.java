package mealplanner.main;

import mealplanner.meal.Meal;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

  /**
   *
   *
   *
   *
   * !! THINGS TO WORK ON !!
   * The addMeal method
   * Making it so commas are accepted when inputting the ingredients
   * Create seperate methods for getting the name of a meal and getting the ingredients of a meal. These should go into the meal user input class, so the user input class should be deprecated
   * If there are no saved meals, only print "No meals saved. Add a meal first."
   *
   *
   *
   * 
   *
   */
  static final Scanner scanner = new Scanner(System.in);
  // Contains all saved meals
  static final ArrayList<Meal> savedMeals = new ArrayList<>();

  public static void main(String[] args) {
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
      System.out.println("What would you like to do (add, show, exit)");
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
   * Adds a new meal to the savedMeals ArrayList
   */
  private static void addMeal() {
    System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
    String category = scanner.nextLine();
    System.out.println("Input the meal's name:");
    String name = scanner.nextLine();
    System.out.println("Input the ingredients:");
    String[] ingredients = scanner.nextLine().split(",");
    return;
  }

  /**
   * Shows all the saves meals in the savedMeals ArrayList
   */
  private static void showMeals() {
    for (Meal meal : savedMeals) {
      System.out.printf("Category: %s", meal.getCategory());
      System.out.printf("Name: %s", meal.getName());
      System.out.println("Ingredients:");
      for (String ingredient : meal.getIngredients()) {
        System.out.println(ingredient);
      }
    }
  }
}