package mealplanner.meal.datamanager.legacydatamanager;

import mealplanner.main.MealUserInput;
import org.postgresql.ds.PGSimpleDataSource;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the meal database. It lets you get data, insert data, modify data, and read data, among other things
 */
public class DataManager {
    /**
     * This enum is used to ensure that correct tables which exist are being chosen
     */
    public enum Tables {
        MEALS, INGREDIENTS
    }

    /**
     * Initializes the required tables if they don't already exist at the beginning of the runtime
     */
    public static void initializeTables() {
        try (Connection connection = connect()) {
            String mealsTableCreation = "CREATE TABLE IF NOT EXISTS meals (" +
                                            "category VARCHAR(9)," +
                                            "meal VARCHAR(50)," +
                                            "meal_id INTEGER" +
                                        ")";
            String ingredientsTableCreation = "CREATE TABLE IF NOT EXISTS ingredients (" +
                                                    "ingredient VARCHAR(50)," +
                                                    "ingredient_id INTEGER," +
                                                    "meal_id INTEGER" +
                                                ")";
            String planTableCreation = "CREATE TABLE IF NOT EXISTS plan (" +
                                            "category VARCHAR(9)," +
                                            "meal VARCHAR(50)," +
                                            "meal_id INTEGER," +
                                            "day VARCHAR(9)" +
                                        ")";
            try (PreparedStatement mealsTableStatement = connection.prepareStatement(mealsTableCreation);
                 PreparedStatement ingredientsTableStatement = connection.prepareStatement(ingredientsTableCreation);
                 PreparedStatement planTableStatement = connection.prepareStatement(planTableCreation)) {
                // Initialization
                mealsTableStatement.execute();
                ingredientsTableStatement.execute();
                planTableStatement.execute();
            } catch (SQLException ignored) {
            }
        } catch (SQLException ignored) {
        }
    }

    /**
     * Returns a ResultSet of all the columns from the specified table
     * @param table the name of the table which the user wishes to select data from
     * @return the ResultSet from the specified table
     */
    @Deprecated
    public static ResultSet getAllColumns(Tables table) {
        try (Connection connection = connect()) {
            if (table.equals(Tables.MEALS)) {
                String selection = "SELECT * FROM meals";
                try (PreparedStatement preparedStatement = connection.prepareStatement(selection)) {
                    return preparedStatement.executeQuery();
                } catch (SQLException ignored) {
                }
            } else if (table.equals(Tables.INGREDIENTS)) {
                String selection = "SELECT * FROM ingredients";
                try (PreparedStatement preparedStatement = connection.prepareStatement(selection)) {
                    return preparedStatement.executeQuery();
                } catch (SQLException ignored) {
                }
            }

        } catch (SQLException ignored) {
        }
        return null;
    }

    /**
     * Returns all the data in the meals table in the specified category as a string for use with the show operation.
     * @param category the category in which only meals from that category are read
     */
    public static String getMealsMessage(String category) {
        StringBuilder message = new StringBuilder();
        // Keeps track of how many meals there are so the spacing is correct. If there is only one meal, there should not be any padding. If there is more than one meal, there should be padding
        int mealCounter = 0;
        // Points to where to place the padding in-between the category and the first name if there is more than one meal
        int firstPaddingPointer = 0;
        try (Connection connection = connect()) {
            String mealSelection = "SELECT * FROM meals WHERE category = '" + category + "'";
            String ingredientSelection = "SELECT * FROM ingredients WHERE meal_id = ? ORDER BY ingredient_id";
            try (PreparedStatement mealStatement = connection.prepareStatement(mealSelection); PreparedStatement ingredientStatement = connection.prepareStatement(ingredientSelection)) {
                // Result of meal selection
                try (ResultSet mealData = mealStatement.executeQuery()) {
                    message.append("Category: ").append(category).append("\n");
                    // Places the pointer in-between the category and the first name
                    firstPaddingPointer = message.length();
                    while (mealData.next()) {
                        ++mealCounter;
                        message.append("Name: ").append(mealData.getString("meal")).append("\n");
                        message.append("Ingredients: \n");
                        ingredientStatement.setInt(1, mealData.getInt("meal_id"));
                        try (ResultSet ingredientData = ingredientStatement.executeQuery()) {
                            while (ingredientData.next()) {
                                message.append(ingredientData.getString("ingredient")).append("\n");
                            }
                        } catch (Exception ignored) {
                        }
                        message.append("\n");
                    }
                } catch (SQLException ignored) {
                }
            } catch (SQLException ignored) {
            }
        } catch (SQLException ignored) {
        }
        // Adds finishing touches to the message based on how many meals there are
        if (mealCounter == 0) {
            return "No meals found.";
        } else if (mealCounter == 1) {
            // Removes all the padding at the end of the message so the message is flush with the next operation request
            message.delete(message.length() - 2, message.length());
        } else {
            // Adds the first padding at the pointer
            message.insert(firstPaddingPointer, "\n");
            // Deletes the extra newline so there aren't two newlines at the end
            message.delete(message.length() - 1, message.length());
        }
        return message.toString();
    }

    /**
     * Gets a String representation of the entire plan for the week for use with the plan operation
     * @return the String representation of plan
     */
    public static String getPlanMessage() throws SQLException {
        StringBuilder message = new StringBuilder();
        try (Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM plan");
            ResultSet result = statement.executeQuery()
        ) {
            result.next();
            for (int i = 0; i < 7; i++) {
                message.append(StringUtils.capitalize(result.getString("day"))).append("\n");
                for (int j = 0; j < 3; j++) {
                    message.append(StringUtils.capitalize(result.getString("category"))).append(": ").append(result.getString("meal")).append("\n");
                    result.next();
                }
                message.append("\n");
            }
            // Deletes the trailing newline. The result is only one newline at the end rather than two
            message.delete(message.length() - 1, message.length());
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return message.toString();
    }

    /**
     * Inserts passed data into the specified table. Uses the Tables enum to specify which table it should insert the data into. The data is parsed differently depending on which table it is being
     * put into
     * @param table the table that the user wants to insert data into
     * @param values a string array consisting of all data the user wants to insert
     */
    public static void insertInto(Tables table, String[] values) {
        try (Connection connection = connect()) {
            // If the passed table is the meals table, then the data is parsed specially for that table
            if (table.equals(Tables.MEALS)) {
                String insert = "INSERT INTO meals VALUES(?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
                    preparedStatement.setString(1, values[0]);
                    preparedStatement.setString(2, values[1]);
                    preparedStatement.setInt(3, Integer.parseInt(values[2]));
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.getMessage();
                }
            // If the passed table is the ingredients table, then the data is parsed specially for that table
            } else if (table.equals(Tables.INGREDIENTS)) {
                String insert = "INSERT INTO ingredients VALUES(?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
                    preparedStatement.setString(1, values[0]);
                    preparedStatement.setInt(2, Integer.parseInt(values[1]));
                    preparedStatement.setInt(3, Integer.parseInt(values[2]));
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.getMessage();
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    /**
     * Gets all meals in the passed category and returns them as a string array. The meals will be in alphabetical order
     * @param category the category from which to get the meals
     * @return the string array of the meals
     * @throws SQLException;
     */
    public static String[] getMealsAlphabeticalOrder(String category) throws SQLException {
        String query;
        // The message which will be returned
        List<String> meals = new ArrayList<>();
        // Checks which category was passed and sets the query accordingly
        if (category.equalsIgnoreCase(MealUserInput.Categories.BREAKFAST.getCategory())) {
            query = "SELECT meal FROM meals WHERE category = 'breakfast' ORDER BY meal";
        } else if (category.equalsIgnoreCase(MealUserInput.Categories.LUNCH.getCategory())) {
            query = "SELECT meal FROM meals WHERE category = 'lunch' ORDER BY meal";
        } else if (category.equalsIgnoreCase(MealUserInput.Categories.DINNER.getCategory())) {
            query = "SELECT meal FROM meals WHERE category = 'dinner' ORDER BY meal";
        } else {
            throw new IllegalArgumentException("The passed category was incorrect. It needs to be either \"breakfast\", \"lunch\", or \"dinner\". Case does not matter.");
        }
        try (Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery()
        ) {
            while (result.next()) {
                meals.add(result.getString("meal"));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        String[] array = new String[meals.size()];
        for (int i = 0; i < meals.size(); i++) {
            array[i] = meals.get(i);
        }
        return array;
    }

    /**
     * Takes the name of a saved meal and returns the corresponding meal_id
     * @param mealName the meal name to get the meal_id from
     * @return the meal_id of the meal
     */
    public static int getMealIDFromName(String mealName) {
        String query = String.format(
                "SELECT meal_id FROM meals WHERE UPPER(meal) = UPPER('%s')", mealName
        );
        try (Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery()
        ) {
            result.next();
            return result.getInt("meal_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the next meal id to be used when adding a meal to the database
     * @return the meal id
     */
    public static int getNextMealID() {
        try (Connection connection = connect()) {
            String selection = "SELECT MAX(meal_id) FROM meals";
            try (PreparedStatement statement = connection.prepareStatement(selection)) {
                try (ResultSet result = statement.executeQuery()) {
                    result.next();
                    return result.getInt("max") + 1;
                } catch (SQLException ignored) {
                }
            } catch (SQLException ignored) {
            }
        } catch (SQLException ignored) {
        }
        return 0;
    }

    /**
     * Gets the next ingredient id to be used when adding an ingredient to the database
     * @return the ingredient id
     */
    public static int getNextIngredientID() {
        try (Connection connection = connect()) {
            String selection = "SELECT MAX(ingredient_id) FROM ingredients";
            try (PreparedStatement statement = connection.prepareStatement(selection)) {
                try (ResultSet result = statement.executeQuery()) {
                    result.next();
                    return result.getInt("max") + 1;
                } catch (SQLException ignored) {
                }
            } catch (SQLException ignored) {
            }
        } catch (SQLException ignored) {
        }
        return 0;
    }

    /**
     * Returns a connection object to be used in other methods. Needs to be used to a try-catch block because one is not used here
     * @return the connection object
     * @throws SQLException;
     */
    private static Connection connect() throws SQLException {
        String DB_URL = "jdbc:postgresql:meals_db";
        String USER = "postgres";
        String PASS = "1111";
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(DB_URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASS);
        return dataSource.getConnection(USER, PASS);
    }
}
