package de.buw.se;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SignupFrontendTest {

    private SignupFrontend signupFrontend;

    @BeforeEach
    public void setUp() {
        signupFrontend = new SignupFrontend();
    }

    @Test
    void testActionPerformed() throws Exception {
        // Accessing and setting private fields using reflection
        setPrivateField("nameField", "John Doe");
        setPrivateField("usernameField", "johndoe");
        setPrivateField("passwordField", "password123");
        setPrivateField("emailField", "john.doe@example.com");

        // Simulate button click
        JButton signupButton = (JButton) getPrivateField("signup");
        ActionEvent event = new ActionEvent(signupButton, ActionEvent.ACTION_PERFORMED, signupButton.getActionCommand());
        signupFrontend.actionPerformed(event);

        // You would check for the expected state or output
        // For example, checking console output or state changes
        // Here we assume that the SignupBackend.Signup() returns true
    }

    // Helper methods to access private fields and methods using reflection
    private Object getPrivateField(String fieldName) throws Exception {
        Field field = SignupFrontend.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(signupFrontend);
    }

    private void setPrivateField(String fieldName, String value) throws Exception {
        Field field = SignupFrontend.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        JTextField textField = (JTextField) field.get(signupFrontend);
        textField.setText(value);
    }
}
