package mealplanner.meal.datamanager.dao.plan;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles everything to do with connecting to the database and executing passed queries for the plans table. The DAO itself doesn't actually execute queries. Rather, it passes queries
 * to this object to be executed.
 */
public class DbPlanClient {
    private final PGSimpleDataSource dataSource;

    public DbPlanClient() {
        String DB_URL = "jdbc:postgresql:meals_db";
        String USER = "postgres";
        String PASS = "1111";
        this.dataSource = new PGSimpleDataSource();
        dataSource.setURL(DB_URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASS);
    }

    public void run(String query) {
        try (Connection connection = dataSource.getConnection();
         Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Plan select(String query) {
        List<Plan> plans = selectForList(query);
        if (plans.size() == 1) {
            return plans.get(0);
        } else if (plans.isEmpty()) {
            return null;
        } else {
            throw new IllegalStateException("Query returned more than one object");
        }
    }

    public List<Plan> selectForList(String query) {
        List<Plan> plans = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
             ResultSet result = statement.executeQuery()
        ) {
            while (result.next()) {
                String category = result.getString("category");
                String meal = result.getString("meal");
                int meal_id = result.getInt("meal_id");
                String day = result.getString("day");
                plans.add(new Plan(category, meal, meal_id, day));
            }
            return plans;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plans;
    }
}
