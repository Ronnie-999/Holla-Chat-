package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChatBackendTest {

    @BeforeEach
    void setUp() throws SQLException {
        // Ensure the messages table is created before each test
        DataBase.createMessagesTable();
        clearMessagesTable();
    }

    @Test
    void testStoreMessage() throws SQLException {
        // Store a message
        ChatBackend.storeMessage("sender1", "receiver1", "Hello, receiver!");

        // Verify that the message is stored correctly
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/messages;AUTO_SERVER=TRUE");
             Statement sql = conn.createStatement();
             ResultSet resultSet = sql.executeQuery("SELECT * FROM messages WHERE sender = 'sender1' AND receiver = 'receiver1'")) {

            assertTrue(resultSet.next());
            assertEquals("Hello, receiver!", resultSet.getString("message"));
        }
    }

    @Test
    void testFetchMessages() throws SQLException {
        // Store messages
        ChatBackend.storeMessage("sender1", "receiver1", "Hello, receiver!");
        ChatBackend.storeMessage("receiver1", "sender1", "Hi, sender!");

        // Fetch messages between sender1 and receiver1
        List<String> messages = ChatBackend.fetchMessages("sender1", "receiver1");

        // Verify the messages are fetched correctly
        assertEquals(2, messages.size());
        assertEquals("sender1: Hello, receiver!", messages.get(0));
        assertEquals("receiver1: Hi, sender!", messages.get(1));
    }

    @Test
    void testSearchMessages() throws SQLException {
        // Store messages
        ChatBackend.storeMessage("sender1", "receiver1", "Hello, receiver!");
        ChatBackend.storeMessage("receiver1", "sender1", "Hi, sender!");
        ChatBackend.storeMessage("sender1", "receiver1", "Are you there?");

        // Search messages containing the word "Hi"
        List<String> messages = ChatBackend.searchMessages("sender1", "Hi");

        // Verify the messages containing the word "Hi" are fetched correctly
        assertEquals(1, messages.size());
        assertEquals("receiver1: Hi, sender!", messages.get(0));
    }

    private void clearMessagesTable() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/messages;AUTO_SERVER=TRUE");
             Statement sql = conn.createStatement()) {
            sql.executeUpdate("DELETE FROM messages");
        }
    }
}
