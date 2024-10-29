package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SignupBackendTest {

    @BeforeEach
    void setUp() throws SQLException {
        // Ensure the users table is created and clear any previous data
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/Users1;AUTO_SERVER=TRUE");
             Statement stmt = conn.createStatement()) {
            DataBase.createUsersTable(stmt);
            clearUsersTable(stmt);
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Clear the users table after each test
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/Users1;AUTO_SERVER=TRUE");
             Statement stmt = conn.createStatement()) {
            clearUsersTable(stmt);
        }
    }

    @Test
    void testSuccessfulSignup() throws SQLException {
        // Verify successful signup
        assertTrue(SignupBackend.Signup("John Doe", "john", "password123", "john@example.com"));

        // Verify that the user details are stored correctly in the database
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/Users1;AUTO_SERVER=TRUE");
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery("SELECT * FROM USERS1 WHERE user_name = 'john'")) {

            assertTrue(resultSet.next());
            assertEquals("John Alen", resultSet.getString("name"));
            assertEquals("john", resultSet.getString("user_name"));
            assertEquals("password123", resultSet.getString("password"));
            assertEquals("john@example.com", resultSet.getString("email"));
        }
    }

    @Test
    void testFailedSignupDueToSqlException() {
        // Simulate an SQL exception by using a very long username to exceed the database column limit
        String longUsername = "a".repeat(300); // Assuming the username column has a limit less than 300
        assertFalse(SignupBackend.Signup("John Doe", longUsername, "password123", "john@example.com"));
    }

    private void clearUsersTable(Statement stmt) throws SQLException {
        stmt.executeUpdate("DELETE FROM USERS1");
    }
}

