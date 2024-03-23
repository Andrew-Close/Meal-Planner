package mealplanner.meal.datamanager.dao.plan;

import java.sql.SQLException;
import java.util.List;

/**
 * This is an interface for dao's for the plan table
 */
public interface PlanDao {
    List<Plan> findAll() throws SQLException;
    Plan find(String category, String day) throws SQLException;
    void add(Plan plan) throws SQLException;
    void update(Plan plan) throws SQLException;
    void delete(int id) throws SQLException;
    void deleteAll() throws SQLException;
}
