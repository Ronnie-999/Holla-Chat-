package de.buw.se;

import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class SignupBackend {

    public static boolean Signup(String name, String username, String password, String email) {
        // Check if username or password is empty
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username and password cannot be empty.");
            return false;
        }

        try {
            Statement sql = DataBase.getSqlStatement();
            String query = "INSERT INTO USERS1 (name, user_name, password, email) VALUES ('" + name + "', '" + username + "', '" + password + "', '" + email + "')";
            int rowsAffected = sql.executeUpdate(query);
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Registration successful!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Registration failed due to an error. Please try again later.");
            return false;
        }
    }
}
