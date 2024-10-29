package de.buw.se;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DataBase {
	private static final String FILE_NAME_USERS = "src/main/resources/Users1";
	private static final String FILE_NAME_MESSAGES = "src/main/resources/messages";

	public static Statement getSqlStatement() {
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:./" + FILE_NAME_USERS + ";AUTO_SERVER=TRUE");
			Statement stmt = conn.createStatement();
			createUsersTable(stmt); // Create USERS1 table if it doesn't exist
			return stmt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void createUsersTable(Statement stmt) {
		try {
			String createUsersTable = "CREATE TABLE IF NOT EXISTS USERS1 (" +
					"user_name VARCHAR(100) PRIMARY KEY," +
					"name VARCHAR(100) NOT NULL," +
					"email VARCHAR(100) NOT NULL UNIQUE," +
					"password VARCHAR(100) NOT NULL" +
					")";
			stmt.executeUpdate(createUsersTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void createMessagesTable() {
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:./" + FILE_NAME_MESSAGES + ";AUTO_SERVER=TRUE");
			Statement stmt = conn.createStatement();
			String createMessagesTable = "CREATE TABLE IF NOT EXISTS messages (" +
					"id INT AUTO_INCREMENT PRIMARY KEY," +
					"sender VARCHAR(100) NOT NULL," +
					"receiver VARCHAR(100) NOT NULL," +
					"message TEXT NOT NULL," +
					"timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
			stmt.executeUpdate(createMessagesTable);
			stmt.close();
			conn.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void showSuccessMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	// Modify updateUsername method
	public static void updateUsername(String oldUsername, String newUsername) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:h2:./" + FILE_NAME_USERS + ";AUTO_SERVER=TRUE");
			Statement stmt = conn.createStatement();
			String updateQuery = "UPDATE USERS1 SET user_name = '" + newUsername + "' WHERE user_name = '" + oldUsername
					+ "'";
			int rowsAffected = stmt.executeUpdate(updateQuery);
			if (rowsAffected > 0) {
				showSuccessMessage("Username updated successfully.");
			} else {
				showErrorMessage("Failed to update username.");
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updatePassword(String username, String newPassword) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:h2:./" + FILE_NAME_USERS + ";AUTO_SERVER=TRUE");
			String updateQuery = "UPDATE USERS1 SET password = ? WHERE user_name = ?";
			PreparedStatement pstmt = conn.prepareStatement(updateQuery);
			pstmt.setString(1, newPassword);
			pstmt.setString(2, username);
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				showSuccessMessage("Password updated successfully.");
			} else {
				showErrorMessage("Failed to update password.");
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Example usage
		updateUsername("oldUsername", "newUsername");
		updatePassword("username", "newPassword");
	}
}
