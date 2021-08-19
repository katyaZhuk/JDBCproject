package tests;

import db_connection.JDBCConnection;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataBaseTest {

    @Test
    @Order(1)
    public void createTableTest() {
        String query = "CREATE TABLE actors (" +
                "ID int(6) NOT NULL AUTO_INCREMENT," +
                "FIRST_NAME VARCHAR(45) NOT NULL," +
                "LAST_NAME VARCHAR(45) NOT NULL," +
                "COUNTRY VARCHAR(45)," +
                "PRIMARY KEY (id))";
        JDBCConnection.createTable(query);
    }

    @Test
    @Order(2)
    public void insertRequestTest() {
        String query = "INSERT INTO actors (FIRST_NAME, LAST_NAME, COUNTRY)" +
                "VALUES ('Merline', 'Monro', 'USA')";
        JDBCConnection.insertIntoTable(query);

        String selectQuery = "SELECT * FROM actors";
        ResultSet rs = JDBCConnection.selectFromTable(selectQuery);
        assertAll("Should return inserted data",
                () -> assertEquals("Merline", rs.getString("FIRST_NAME")),
                () -> assertEquals("Monro", rs.getString("LAST_NAME")),
                () -> assertEquals("USA", rs.getString("COUNTRY")));
    }

    @Test
    @Order(3)
    public void updateRequestTest() throws SQLException {
        String query = "UPDATE actors " +
                "SET COUNTRY = 'Canada' " +
                "WHERE ID='1'";
        JDBCConnection.updateTable(query);

        String selectQuery = "SELECT COUNTRY FROM actors " +
                "WHERE ID='1'";
        ResultSet rs = JDBCConnection.selectFromTable(selectQuery);
        String expectedCountry = "Canada";
        String actualCountry = rs.getString("COUNTRY");
        assertEquals(expectedCountry, actualCountry);
    }

    @Test
    @Order(4)
    public void deleteRequestTest() {
        String query = "DELETE FROM actors WHERE ID='1'";
        JDBCConnection.deleteFromTable(query);
    }

    @Test
    @Order(5)
    public void dropTableTest() {
        JDBCConnection.dropTable("actors");
    }

    @Test
    @Order(6)
    public void selectWithConditionTest() throws SQLException {
        String selectQuery = "SELECT * FROM actor " +
                "WHERE last_name='DEPP'";
        ResultSet rs = JDBCConnection.selectFromTable(selectQuery);
        String expectedLastName = "DEPP";
        String actualCLastName = rs.getString("last_name");
        assertEquals(expectedLastName, actualCLastName);
    }

    @Test
    @Order(7)
    public void selectWithJoinTest() throws SQLException {
        String selectQuery = "SELECT city.*, country.country from city " +
                "left join country " +
                "on city.country_id = country.country_id " +
                "where country = 'Belarus'";
        ResultSet rs = JDBCConnection.selectFromTable(selectQuery);
        String expectedCountry = "Belarus";
        String actualCountry = rs.getString("country");
        assertEquals(expectedCountry, actualCountry);
    }

}
