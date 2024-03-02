package mealplanner.meal;

public class Meal {
    // All meals contain what category it is (breakfast, lunch, dinner), what the name is, and what ingredients it needs
    private String category;
    private String name;
    private String[] ingredients;

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
