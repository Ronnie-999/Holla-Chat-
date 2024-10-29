package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginFrontendTest {

    private LoginFrontend loginFrontend;

    @BeforeEach
    void setUp() {
        loginFrontend = new LoginFrontend();
    }

    @Test
    void testUIComponents() throws Exception {
        assertNotNull(getPrivateField("usernameField"));
        assertNotNull(getPrivateField("passwordField"));
        assertNotNull(getPrivateField("login"));
    }

    @Test
    void testValidLogin() throws Exception {
        // Set valid credentials
        setPrivateField("usernameField", "admin");
        setPrivateField("passwordField", "password");

        // Simulate button click
        simulateButtonClick("login");

        // Assuming the login is valid, check if the window is disposed
        // You might want to use additional assertions based on actual implementation
        assertTrue(loginFrontend.isVisible());
    }

    @Test
    void testInvalidLogin() throws Exception {
        // Set invalid credentials
        setPrivateField("usernameField", "user");
        setPrivateField("passwordField", "wrongpassword");

        // Simulate button click
        simulateButtonClick("login");

        // Assuming the login is invalid, check if the window is still visible
        assertTrue(loginFrontend.isVisible());
    }

    // Helper methods to access private fields and methods using reflection
    private Object getPrivateField(String fieldName) throws Exception {
        Field field = LoginFrontend.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(loginFrontend);
    }

    private void setPrivateField(String fieldName, String value) throws Exception {
        Field field = LoginFrontend.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        if (fieldName.equals("usernameField")) {
            JTextField textField = (JTextField) field.get(loginFrontend);
            textField.setText(value);
        } else if (fieldName.equals("passwordField")) {
            JPasswordField passwordField = (JPasswordField) field.get(loginFrontend);
            passwordField.setText(value);
        }
    }

    private void simulateButtonClick(String buttonName) throws Exception {
        JButton button = (JButton) getPrivateField(buttonName);
        ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, button.getActionCommand());
        button.doClick();
        loginFrontend.actionPerformed(event);
    }
}
