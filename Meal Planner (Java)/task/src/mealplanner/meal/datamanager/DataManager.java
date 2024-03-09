package mealplanner.meal.datamanager;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class manages the meal database. It lets you get data, insert data, and modify data
 */
public class DataManager {
    /**
     * This enum is used to ensure that correct tables which exist are being chosen
     */
    public enum Tables {
        MEALS, INGREDIENTS
    }

    /**
     * Returns a ResultSet of all the columns from the specified table
     * @param table = the name of the table which the user wishes to select data from
     * @return = the ResultSet from the specified table
     */
    public static ResultSet getAllColumns(String table) {
        try (Connection connection = connect()) {
            String selection = "SELECT * FROM ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selection)) {
                preparedStatement.setString(1, table);
                return preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserts passed data into the specified table. Uses the Tables enum to specify which table it should insert the data into. The data is parsed differently depending on which table it is being
     * put into
     * @param table = the table that the user wants to insert data into
     * @param values = a string array consisting of all data the user wants to insert
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
     * Returns a connection object to be used in other methods. Needs to be used to a try-catch block because one is not used here
     * @return = the connection object
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
