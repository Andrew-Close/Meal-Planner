package mealplanner.meal.datamanager;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class manages the meal database. It lets you get data, insert data, modify data, and read data
 */
public class DataManager {
    /**
     * This enum is used to ensure that correct tables which exist are being chosen
     */
    public enum Tables {
        MEALS, INGREDIENTS
    }

    private static int nextMealID = 1;
    private static int nextIngredientID = 1;

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
                    /*
                    while (result.next()) {
                        System.out.printf("Category: %s%n", result.getString("category"));
                        System.out.printf("Name: %s%n", result.getString("meal"));
                        System.out.printf("Id: %d%n", result.getInt("meal_id"));
                    }
                     */
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (table.equals(Tables.INGREDIENTS)) {
                String selection = "SELECT * FROM ingredients";
                try (PreparedStatement preparedStatement = connection.prepareStatement(selection)) {
                    return preparedStatement.executeQuery();
                    /*
                    while (result.next()) {
                        System.out.printf("Ingredient: %s%n", result.getString("ingredient"));
                        System.out.printf("Ingredient_id: %s%n", result.getInt("ingredient_id"));
                        System.out.printf("Id: %d%n", result.getInt("meal_id"));
                    }
                     */
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Prints out all data in the specified table. Data is parsed differently depending on the input table
     * @param table the table which should be read from
     * @throws SQLException;
     */
    public static void printData(Tables table) throws SQLException {
        try (Connection connection = connect()) {
            // Branch for if the input table is the meals table
            if (table.equals(Tables.MEALS)) {
                String selection = "SELECT * FROM meals";
                try (PreparedStatement preparedStatement = connection.prepareStatement(selection)) {
                    // Result of selection
                    try (ResultSet data = preparedStatement.executeQuery()) {
                        // Here is where the data gets printed out
                        while (data.next()) {
                            System.out.printf("Category: %s%n", data.getString("category"));
                            System.out.printf("Name: %s%n", data.getString("meal"));
                            System.out.printf("Id: %d%n", data.getInt("meal_id"));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            // Branch for if the input table is the ingredients table
            } else if (table.equals(Tables.INGREDIENTS)) {
                String selection = "SELECT * FROM ingredients";
                try (PreparedStatement preparedStatement = connection.prepareStatement(selection)) {
                    // Result of selection
                    try (ResultSet data = preparedStatement.executeQuery()) {
                        // Here is where the data gets printed out
                        while (data.next()) {
                            System.out.printf("Ingredient: %s%n", data.getString("ingredient"));
                            System.out.printf("Ingredient_id: %s%n", data.getInt("ingredient_id"));
                            System.out.printf("Id: %d%n", data.getInt("meal_id"));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public static int nextMealID() {
        ++nextMealID;
        return nextMealID - 1;
    }

    public static int nextIngredientID() {
        ++nextIngredientID;
        return nextIngredientID - 1;
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
