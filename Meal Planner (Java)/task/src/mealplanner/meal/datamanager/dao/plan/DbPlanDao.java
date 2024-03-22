package mealplanner.meal.datamanager.dao.plan;

import java.util.List;

/*






Change the select and delete methods to use the day and category to find the right row rather than the id. Review the stage instructions first though.








 */

/**
 * This class interfaces directly with the user, allowing them access to basic CRUD operations. This class uses a client to send queries to the database
 */
public class DbPlanDao implements PlanDao {
    private final DbPlanClient client;

    public DbPlanDao() {
        client = new DbPlanClient();
    }


    @Override
    public List<Plan> findAll() {
        return client.selectForList("SELECT * FROM plan");
    }

    @Override
    public Plan find(int id) {
        return client.select(String.format("SELECT * FROM plan WHERE meal_id = %d", id));
    }

    @Override
    public void add(Plan plan) {
        client.run(String.format("INSERT INTO plan VALUES('%s', '%s', %d, '%s')", plan.getCategory(), plan.getMeal(), plan.getMeal_id(), plan.getDay()));
    }

    @Override
    public void update(Plan plan) {
        client.run(String.format("UPDATE plan SET meal = '%s', meal_id = %d WHERE category = '%s' AND day = '%s'", plan.getMeal(), plan.getMeal_id(), plan.getCategory(), plan.getDay()));
    }

    @Override
    public void delete(int id) {
        client.run(String.format("DELETE FROM plan WHERE meal_id = %d", id));
    }
}
