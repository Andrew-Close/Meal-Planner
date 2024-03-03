package mealplanner.meal;

/**
 * This class simply stores all the necessary fields for a meal. It does nothing else
 */
public class Meal {
    // All meals contain what category it is (breakfast, lunch, dinner), what the name is, and what ingredients it needs
    private final String category;
    private final String name;
    private final String[] ingredients;

    public Meal(String category, String name, String[] ingredients) {
        this.category = category;
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String[] getIngredients() {
        return ingredients;
    }
}
