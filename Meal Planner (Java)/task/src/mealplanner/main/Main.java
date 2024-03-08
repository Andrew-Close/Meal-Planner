package mealplanner.main;

import mealplanner.meal.Meal;
import mealplanner.userinput.UserInput;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class Main {
  // Contains all saved meals
  static final ArrayList<Meal> savedMeals = new ArrayList<>();
  static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) throws SQLException {
    /*
    All of this is a test for connecting with the database and sending queries to it. None of this relates to the actual task, it's just a test for learning how to do things.
     */
    String DB_URL = "jdbc:postgresql:meals_db";
    String USER = "postgres";
    String PASS = "1111";

    PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setURL(DB_URL);
    dataSource.setUser(USER);
    dataSource.setPassword(PASS);
    try (Connection connection = dataSource.getConnection(USER, PASS)) {
      String statement = "INSERT INTO test_table VALUES(?, ?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
        preparedStatement.setString(1, scanner.nextLine());
        preparedStatement.setInt(2, scanner.nextInt());
        preparedStatement.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      String selection = "SELECT * FROM test_table";
      try (PreparedStatement preparedStatement = connection.prepareStatement(selection)) {
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
          System.out.printf("name: %s%n", result.getString("name"));
          System.out.printf("id: %d%n%n", result.getInt("id"));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

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
    savedMeals.add(new Meal(category, name, ingredients));
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
        System.out.printf("Category: %s%n", meal.getCategory());
        System.out.printf("Name: %s%n", meal.getName());
        System.out.println("Ingredients:");
        for (String ingredient : meal.getIngredients()) {
          System.out.println(ingredient);
        }
      }
      System.out.println();
    }
  }
}