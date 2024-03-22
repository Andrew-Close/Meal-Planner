package mealplanner.meal.datamanager.dao.plan;

import java.util.List;

/**
 * This is an interface for dao's for the plan table
 */
public interface PlanDao {
    List<Plan> findAll();
    Plan find(int id);
    void add(Plan plan);
    void update(Plan plan);
    void delete(int id);
}
