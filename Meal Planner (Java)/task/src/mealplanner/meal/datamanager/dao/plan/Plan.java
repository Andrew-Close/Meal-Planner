package mealplanner.meal.datamanager.dao.plan;

/**
 * This is just a class for holding fields for the plan for the database
 */
public class Plan {
    String category;
    String meal;
    int meal_id;
    String day;

    public Plan(String category, String meal, int meal_id, String day) {
        this.category = category;
        this.meal = meal;
        this.meal_id = meal_id;
        this.day = day;
    }

    String getCategory() {
        return category;
    }

    String getMeal() {
        return meal;
    }

    int getMeal_id() {
        return meal_id;
    }

    String getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "category='" + category + '\'' +
                ", meal='" + meal + '\'' +
                ", meal_id=" + meal_id +
                ", day='" + day + '\'' +
                '}';
    }
}
