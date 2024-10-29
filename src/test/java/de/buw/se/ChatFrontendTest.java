package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChatFrontendTest {

    private ChatFrontend chatFrontend;
    private JTextField messagebox;
    private JTextArea chatscreen;

    @BeforeEach
    void setUp() {
        chatFrontend = new ChatFrontend("sender", "receiver");
        messagebox = getPrivateField("messagebox");
        chatscreen = getPrivateField("chatscreen");
    }

    @Test
    void testUIComponents() {
        assertNotNull(messagebox);
        assertNotNull(chatscreen);
        assertEquals("Chat with receiver", chatFrontend.getTitle());
    }

    @Test
    void testSendMessage() {
        messagebox.setText("Hello, world!");
        JButton sendButton = getButtonByText("Send");
        sendButton.doClick();

        // Manually invoke the private method to verify
        invokePrivateMethod("sendMessage", "Hello, world!");

        // Verify that the messagebox is cleared
        assertEquals("", messagebox.getText());
    }

    @Test
    void testSendEmoji() {
        JButton emojiButton = getButtonByText("\uD83D\uDE00"); // Grinning face
        emojiButton.doClick();

        // Manually invoke the private method to verify
        invokePrivateMethod("sendMessage", "\uD83D\uDE00");

        // Verify that the messagebox is cleared
        assertEquals("", messagebox.getText());
    }

    @Test
    void testRefreshMessages() {
        // Simulate backend messages
        List<String> mockMessages = Arrays.asList("Message 1", "Message 2", "Message 3");
        setBackendMessages(mockMessages);

        // Invoke the refreshMessages method
        invokePrivateMethod("refreshMessages");

        String expectedText = "Message 1\nMessage 2\nMessage 3\n";
        assertEquals(expectedText, chatscreen.getText());
    }

    // Helper methods to access private fields and methods using reflection
    private <T> T getPrivateField(String fieldName) {
        try {
            Field field = ChatFrontend.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(chatFrontend);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void invokePrivateMethod(String methodName, Object... args) {
        try {
            var method = ChatFrontend.class.getDeclaredMethod(methodName, String.class);
            method.setAccessible(true);
            method.invoke(chatFrontend, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setBackendMessages(List<String> messages) {
        try {
            Field field = ChatBackend.class.getDeclaredField("messages");
            field.setAccessible(true);
            field.set(null, messages);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JButton getButtonByText(String text) {
        for (var component : chatFrontend.getContentPane().getComponents()) {
            if (component instanceof JPanel) {
                for (var subComponent : ((JPanel) component).getComponents()) {
                    if (subComponent instanceof JButton && ((JButton) subComponent).getText().equals(text)) {
                        return (JButton) subComponent;
                    }
                }
            }
        }
        throw new RuntimeException("Button with text " + text + " not found");
    }
}

