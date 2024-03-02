package mealplanner.main;

import mealplanner.meal.Meal;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
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
      String userInput = scanner.nextLine();
      switch (userInput) {
        case "add":
          addMeal();
          break;
        case "show":

          break;
        case "exit":
          shouldContinue = false;
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