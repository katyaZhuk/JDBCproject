package tests;

import db_connection.JDBCConnection;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static db_connection.JDBCConnection.connectToDB;
import static org.junit.jupiter.api.Assertions.*;
import static utils.PropertiesHelper.*;
import static utils.PropertiesHelper.getJoinCountry;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataBaseTest {

    @Test
    @Order(1)
    public void createTableTest() {
        String query = "CREATE TABLE actors (" +
                "ID int(6) NOT NULL," +
                "FIRST_NAME VARCHAR(45) NOT NULL," +
                "LAST_NAME VARCHAR(45) NOT NULL," +
                "COUNTRY VARCHAR(45)," +
                "PRIMARY KEY (id))";
        JDBCConnection.createTable(query);
    }

    @Test
    @Order(2)
    public void insertRequestTest() throws IOException {
        String query = "INSERT INTO actors (ID, FIRST_NAME, LAST_NAME, COUNTRY)" +
                "VALUES ('" + getID() +
                "', '" + getFirstName() +
                "', '" + getLastName() +
                "', '" + getLastCountry() + "')";
        JDBCConnection.insertIntoTable(query);

        String selectQuery = "SELECT * FROM actors";
        ResultSet rs = JDBCConnection.selectFromTable(selectQuery);
        assertAll("Should return inserted data",
                () -> assertEquals(getID(), rs.getString("ID")),
                () -> assertEquals(getFirstName(), rs.getString("FIRST_NAME")),
                () -> assertEquals(getLastName(), rs.getString("LAST_NAME")),
                () -> assertEquals(getLastCountry(), rs.getString("COUNTRY")));
    }

    @Test
    @Order(3)
    public void updateRequestTest() throws SQLException, IOException {
        String query = "UPDATE actors " +
                "SET COUNTRY = '" + getExpectedCountry() + "' " +
                "WHERE ID='" + getID() + "'";
        JDBCConnection.updateTable(query);

        String selectQuery = "SELECT COUNTRY FROM actors " +
                "WHERE ID='" + getID() + "'";
        ResultSet rs = JDBCConnection.selectFromTable(selectQuery);
        String actualCountry = rs.getString("COUNTRY");
        assertEquals(getExpectedCountry(), actualCountry);
    }

    @Test
    @Order(4)
    public void deleteRequestTest() throws SQLException, IOException {
        String query = "DELETE FROM actors WHERE ID='" + getID() + "'";
        JDBCConnection.deleteFromTable(query);

        String selectQuery = "SELECT COUNT(ID) FROM actors WHERE ID='" + getID() + "'";
        ResultSet rs = JDBCConnection.selectFromTable(selectQuery);
        assertEquals(0, rs.getInt("COUNT(ID)"));
    }

    @Test
    @Order(5)
    public void dropTableTest() throws SQLException {
        JDBCConnection.dropTable("actors");

        DatabaseMetaData md = connectToDB().getMetaData();
        ResultSet rs = md.getTables(null, null, "actors", null);
        int countActorsTables = 0;
        if (rs.next()) {
            countActorsTables ++;
        }

        assertEquals(0, countActorsTables);
    }

    @Test
    @Order(6)
    public void selectWithConditionTest() throws SQLException, IOException {
        String selectQuery = "SELECT * FROM actor " +
                "WHERE last_name='" + getExpectedLastName() + "'";
        ResultSet rs = JDBCConnection.selectFromTable(selectQuery);
        String actualCLastName = rs.getString("last_name");
        assertEquals(getExpectedLastName(), actualCLastName);
    }

    @Test
    @Order(7)
    public void selectWithJoinTest() throws SQLException, IOException {
        String selectQuery = "SELECT city.*, country.country from city " +
                "left join country " +
                "on city.country_id = country.country_id " +
                "where country = '" + getJoinCountry() + "'";
        ResultSet rs = JDBCConnection.selectFromTable(selectQuery);
        String actualCountry = rs.getString("country");
        assertEquals(getJoinCountry(), actualCountry);
    }

}
