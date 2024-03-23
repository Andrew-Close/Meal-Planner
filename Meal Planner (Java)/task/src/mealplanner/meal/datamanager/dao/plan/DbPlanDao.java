package mealplanner.meal.datamanager.dao.plan;

import java.sql.SQLException;
import java.util.List;

/**
 * This class interfaces directly with the user, allowing them access to basic CRUD operations. This class uses a client to send queries to the database
 */
public class DbPlanDao implements PlanDao {
    private final DbPlanClient client;

    public DbPlanDao() {
        client = new DbPlanClient();
    }

    @Override
    public List<Plan> findAll() throws SQLException {
        return client.selectForList("SELECT * FROM plan");
    }

    @Override
    public Plan find(String category, String day) throws SQLException {
        return client.select(String.format("SELECT * FROM plan WHERE category = '%s' AND day = '%s'", category, day));
    }

    @Override
    public void add(Plan plan) throws SQLException {
        client.run(String.format("INSERT INTO plan VALUES('%s', '%s', %d, '%s')", plan.getCategory(), plan.getMeal(), plan.getMeal_id(), plan.getDay()));
    }

    @Override
    public void update(Plan plan) throws SQLException {
        client.run(String.format("UPDATE plan SET meal = '%s', meal_id = %d WHERE category = '%s' AND day = '%s'", plan.getMeal(), plan.getMeal_id(), plan.getCategory(), plan.getDay()));
    }

    @Override
    public void delete(int id) throws SQLException {
        client.run(String.format("DELETE FROM plan WHERE meal_id = %d", id));
    }

    @Override
    public void deleteAll() throws SQLException {
        client.run("DELETE FROM plan");
    }
}
