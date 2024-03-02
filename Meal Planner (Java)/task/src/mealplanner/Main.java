package mealplanner;

import java.util.Scanner;

public class Main {
  static final Scanner scanner = new Scanner(System.in);
  static String category;
  static String name;
  static String[] ingredients;

  public static void main(String[] args) {
    // User input
    System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
    category = scanner.nextLine();
    System.out.println("Input the meal's name:");
    name = scanner.nextLine();
    System.out.println("Input the ingredients:");
    ingredients = scanner.nextLine().split(",");
    // Print the meal information
    printMealInformation();
  }

  public static void printMealInformation() {
    System.out.printf("Category: %s", category);
    System.out.printf("Name: %s", name);
    System.out.println("Ingredients:");
    for (String ingredient : ingredients) {
      System.out.println(ingredient);
    }
    System.out.println("The meal has been added!");
  }
}