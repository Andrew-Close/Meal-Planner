package mealplanner.meal;

/**
 * This class simply stores all the necessary fields for a meal. It does nothing else
 *
 * @param category All meals contain what category it is (breakfast, lunch, dinner), what the name is, and what ingredients it needs
 */
public record Meal(String category, String name, String[] ingredients) {
}
