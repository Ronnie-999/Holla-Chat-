package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.buw.se.LoginBackend.EmptyCredentialsException;

public class LoginBackendTest {

    @BeforeEach
    void setUp() throws SQLException {
        // Ensure the users table is created and clear any previous data
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/Users1;AUTO_SERVER=TRUE");
             Statement stmt = conn.createStatement()) {
            DataBase.createUsersTable(stmt);
            clearUsersTable(stmt);
            insertTestUserData(stmt);
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
    void testSuccessfulAuthentication() throws EmptyCredentialsException {
        // Verify successful authentication with correct credentials
        assertTrue(LoginBackend.authentication("user1", "password1"));
    }

    @Test
    void testFailedAuthenticationDueToIncorrectPassword() throws EmptyCredentialsException {
        // Verify failed authentication due to incorrect password
        assertFalse(LoginBackend.authentication("user1", "wrongPassword"));
    }

    @Test
    void testFailedAuthenticationDueToNonexistentUser() throws EmptyCredentialsException {
        // Verify failed authentication due to non-existent user
        assertFalse(LoginBackend.authentication("nonexistentUser", "password"));
    }

    @Test
    void testSqlExceptionHandling() throws EmptyCredentialsException {
        try {
            // Simulate SQL exception by using an incorrect database URL
            DriverManager.getConnection("jdbc:h2:./invalid/path;AUTO_SERVER=TRUE");
            assertFalse(LoginBackend.authentication("user1", "password1"));
        } catch (SQLException e) {
            assertTrue(e instanceof SQLException);
        }
    }

    private void clearUsersTable(Statement stmt) throws SQLException {
        stmt.executeUpdate("DELETE FROM USERS1");
    }

    private void insertTestUserData(Statement stmt) throws SQLException {
        stmt.executeUpdate("INSERT INTO USERS1 (user_name, password) VALUES ('user1', 'password1'), ('user2', 'password2')");
    }
}
