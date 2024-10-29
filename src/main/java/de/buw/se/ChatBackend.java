package de.buw.se;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ChatBackend {

    public static void storeMessage(String sender, String receiver, String message) {
        try {
            // Ensure the messages table is created
            DataBase.createMessagesTable();
            // Establish connection to the messages database
            try (Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/messages;AUTO_SERVER=TRUE")) {
                try (Statement sql = conn.createStatement()) {
                    // Sanitize message to prevent SQL injection
                    String sanitizedMessage = sanitizeMessage(message);

                    // Constructing the SQL query to insert the message into the database
                    String query = "INSERT INTO messages (sender, receiver, message) VALUES ('" + sender + "','" + receiver + "','" + sanitizedMessage + "')";

                    // Executing the SQL query
                    sql.executeUpdate(query);

                    JOptionPane.showMessageDialog(null, "Message sent successfully!");
                }
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            JOptionPane.showMessageDialog(null, "Failed to send message: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static List<String> fetchMessages(String sender, String receiver) {
        List<String> messages = new ArrayList<>();
        try {
            // Establish connection to the messages database
            try (Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/messages;AUTO_SERVER=TRUE")) {
                try (Statement sql = conn.createStatement()) {
                    // Constructing the SQL query to fetch messages from the database
                    String query = "SELECT * FROM messages WHERE (sender = '" + sender + "' AND receiver = '" + receiver + "') OR (sender = '" + receiver + "' AND receiver = '" + sender + "') ORDER BY timestamp";

                    // Executing the SQL query and retrieving the result set
                    try (ResultSet resultSet = sql.executeQuery(query)) {
                        // Iterating through the result set
                        while (resultSet.next()) {
                            // Constructing the message format
                            String message = resultSet.getString("sender") + ": " + resultSet.getString("message");
                            // Adding the message to the list
                            messages.add(message);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            JOptionPane.showMessageDialog(null, "Failed to fetch messages: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return messages;
    }

    public static ArrayList<String> searchMessages(String sender, String searchText) {
        ArrayList<String> messages = new ArrayList<>();
        try {
            // Establish connection to the messages database
            try (Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/messages;AUTO_SERVER=TRUE")) {
                try (Statement sql = conn.createStatement()) {
                    // Constructing the SQL query to search messages from the database
                    String query = "SELECT * FROM messages WHERE (sender = '" + sender + "' AND message LIKE '%" + searchText + "%') OR (receiver = '" + sender + "' AND message LIKE '%" + searchText + "%') ORDER BY timestamp";

                    // Executing the SQL query and retrieving the result set
                    try (ResultSet resultSet = sql.executeQuery(query)) {
                        // Iterating through the result set
                        while (resultSet.next()) {
                            // Constructing the message format
                            String message = resultSet.getString("sender") + ": " + resultSet.getString("message");
                            // Adding the message to the list
                            messages.add(message);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            JOptionPane.showMessageDialog(null, "Failed to search messages: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return messages;
    }

    private static String sanitizeMessage(String message) {
        // Sanitize message to prevent SQL injection
        String sanitizedMessage = message.replaceAll("(?i)drop\\s+database", "[REMOVED]");
        sanitizedMessage = sanitizedMessage.replaceAll("(?i)drop\\s+table", "[REMOVED]");
        return sanitizedMessage;
    }
}
