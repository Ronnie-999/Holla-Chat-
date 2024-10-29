package de.buw.se;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class responsible for user authentication during login.
 */
public class LoginBackend {
    
    private static final String DB_URL = "jdbc:h2:./src/main/resources/Users1;AUTO_SERVER=TRUE";
    
    /**
     * Custom exception class for empty username or password.
     */
    public static class EmptyCredentialsException extends Exception {
        public EmptyCredentialsException(String message) {
            super(message);
        }
    }
    
    /**
     * Authenticates a user based on provided username and password.
     * 
     * @param username The username of the user.
     * @param password The password of the user.
     * @return True if authentication is successful, false otherwise.
     * @throws EmptyCredentialsException If username or password is empty or null.
     */
    public static boolean authentication(String username, String password) throws EmptyCredentialsException {
        // Check for empty username or password
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new EmptyCredentialsException("Username or password cannot be empty.");
        }
        
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM USERS1 WHERE user_name = ? AND password = ?")) {
            
            statement.setString(1, username);
            statement.setString(2, password);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // true if user exists with the given credentials
            }
        } catch (SQLException e) {
            // Log the exception and handle it gracefully
            e.printStackTrace(); // or use a logging framework
            return false;
        }
    }
}
