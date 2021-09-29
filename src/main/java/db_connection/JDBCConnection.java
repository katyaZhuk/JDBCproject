package db_connection;

import utils.Log;

import java.io.IOException;
import java.sql.*;

import static utils.PropertiesHelper.*;

public class JDBCConnection {

    private static Connection con;
    private static Statement statement;
    private static ResultSet rs;

    public static Connection connectToDB() {
        try {
            Log.info("Connect to " + getURL() + " by user " + getUser());
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(getURL(), getUser(), getPassword());
            Log.info("Connection to DB successful");
        } catch (ClassNotFoundException | IOException | SQLException e) {
            Log.error(e.getMessage());
        }
        return con;
    }

    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                Log.info("Connection to DB closed successfully");
            } catch (SQLException e) {
                Log.error("Connection to DB was not closed\n" + e.getMessage());
            }
        }

        if (statement != null) {
            try {
                statement.close();
                Log.info("Connection to DB closed successfully");
            } catch (SQLException e) {
                Log.error("Connection to DB was not closed\n" + e.getMessage());
            }
        }

        if (rs != null) {
            try {
                rs.close();
                Log.info("Connection to DB closed successfully");
            } catch (SQLException e) {
                Log.error("Connection to DB was not closed\n" + e.getMessage());
            }
        }
    }

    public static void createTable(String query) {
        try {
            statement = connectToDB().prepareStatement(query);
            Log.info("Send request to DB: " + query);
            statement.executeUpdate(query);
            Log.info("Table was created successfully");
        } catch (SQLException e) {
            Log.error("Table was not created\n" + e.getMessage());
        }
    }

    public static void dropTable(String tableName) {
        String query = "DROP TABLE " + tableName;
        try {
            statement = connectToDB().prepareStatement(query);
            Log.info("Send request to DB: " + query);
            statement.executeUpdate(query);
            Log.info("Table was deleted successfully");
        } catch (SQLException e) {
            Log.error("Table was not deleted\n" + e.getMessage());
        }
    }

    public static ResultSet selectFromTable(String query) {
        try {
            statement = connectToDB().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            Log.info("Send request to DB: " + query);
            rs = statement.executeQuery(query);
            rs.next();
        } catch (SQLException e) {
            Log.error(e.getMessage());
        }
        return rs;
    }

    public static void insertIntoTable(String query) {
        try {
            statement = connectToDB().createStatement();
            Log.info("Send request to DB: " + query);
            statement.executeUpdate(query);
            Log.info("New data was inserted into table successfully");
        } catch (SQLException e) {
            Log.error("New data was not inserted into table\n" + e.getMessage());
        }
    }

    public static void updateTable(String query) {
        try {
            statement = connectToDB().createStatement();
            Log.info("Send request to DB: " + query);
            statement.executeUpdate(query);
            Log.info("Data in table was updated successfully");
        } catch (SQLException e) {
            Log.error("Data in table was not updated\n" + e.getMessage());
        }
    }

    public static void deleteFromTable(String query) {
        try {
            statement = connectToDB().createStatement();
            Log.info("Send request to DB: " + query);
            statement.executeUpdate(query);
            Log.info("Data from table was deleted successfully");
        } catch (SQLException e) {
            Log.error("Data from table was not deleted\n" + e.getMessage());
        }
    }
}
