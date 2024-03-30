package mealplanner.main;

import mealplanner.datamanager.dao.plan.DbPlanDao;
import mealplanner.datamanager.dao.plan.Plan;
import mealplanner.datamanager.legacydatamanager.DataManager;
import mealplanner.userinput.UserInput;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
  private static final DbPlanDao planDao = new DbPlanDao();
  private static final Scanner scanner = new Scanner(System.in);
  private static final String operationMessage = "What would you like to do (add, show, plan, save, exit)?";
  public static void main(String[] args) throws SQLException, IOException {
    // Since the test gets rid of all tables at the start of execution, I need to initialize the tables if they don't already exist
    DataManager.initializeTables();
    // The user input loop
    inputLoop();
  }

  /**
   * This is the loop for getting user input and performing actions
   */
  private static void inputLoop() throws SQLException, IOException {
    // The while loop uses this boolean as the condition, so when this boolean is set to false, the loop terminates
    boolean shouldContinue = true;
    while (shouldContinue) {
      //String message = DataManager.getMessage();
      // Caches the message
      System.out.println(operationMessage);
      String operation = MealUserInput.getValidOperation();
      switch (operation) {
        case "add":
          addMeal();
          break;
        case "show":
          showMeals();
          break;
        case "plan":
          planMeals();
          break;
        case "save":
          saveShoppingList();
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
    System.out.println(DataManager.getMealsMessage(MealUserInput.getValidCategory()));
  }

  /**
   * Goes through the planning process to plan meals for each category for each day of the week
   */
  private static void planMeals() throws SQLException {
    planDao.deleteAll();
    // Iterates through each of the days
    for (int i = 1; i <= 7; i++) {
      String day;
      day = getDayFromIteration(i);
      System.out.println(day);
      // Iterates through each of the categories
      for (int j = 1; j <= 3; j++) {
        // Current day
        // Current category
        String category = getCategoryFromIteration(j);
        // The meals from the current category
        String[] meals = DataManager.getMealsAlphabeticalOrder(category);
        for (String meal : meals) System.out.println(meal);
        System.out.printf("Choose the %s for %s from the list above:%n", category.toLowerCase(), day);
        // Gets user input for a meal which is in the meals array
        String choice = MealUserInput.getValidMealUsingArray(meals);
        planDao.add(new Plan(category.toLowerCase(), choice, DataManager.getMealIDFromName(choice), day.toLowerCase()));
      }
      System.out.printf("Yeah! We planned the meals for %s.%n%n", day);
    }
    System.out.println(DataManager.getPlanMessage());
  }

  /**
   * Generates a shopping list of all the ingredients needed for all the meals in the plan and saves the list to a file
   */
  private static void saveShoppingList() throws IOException, SQLException {
    if (DataManager.tableIsEmpty(DataManager.Tables.PLAN)) {
      System.out.println("Unable to save. Plan your meals first.");
    } else {
      System.out.println("Input a filename:");
      String fileName = scanner.nextLine();
      String filePath = String.format(".\\%s", fileName);
      try (Writer fileWriter = new FileWriter(filePath)) {
        fileWriter.write(DataManager.getIngredientFrequencies());
      } catch (IOException e) {
        throw new IOException(e);
      }
      System.out.println("Saved!");
    }
  }

  /**
   * A method to be used with the planMeals method which returns the day corresponding to the iteration of the for loop in the planMeals method, 1-7
   * @param i the iteration
   * @return the day
   */
  private static String getDayFromIteration(int i) {
      return switch (i) {
          case 1 -> "Monday";
          case 2 -> "Tuesday";
          case 3 -> "Wednesday";
          case 4 -> "Thursday";
          case 5 -> "Friday";
          case 6 -> "Saturday";
          case 7 -> "Sunday";
          default -> "Null";
      };
  }

  /**
   * A method to be used with the planMeals method which returns the category corresponding to the iteration of the for loop in the planMeals method, 1-3
   * @param i the iteration
   * @return the category
   */
  private static String getCategoryFromIteration(int i) {
    return switch (i) {
      case 1 -> "Breakfast";
      case 2 -> "Lunch";
      case 3 -> "Dinner";
      default -> "Null";
    };
  }

  public static String getOperationMessage() {
    return operationMessage;
  }
}