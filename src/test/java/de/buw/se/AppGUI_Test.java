package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Frame;
import java.lang.reflect.Field;

import javax.swing.JButton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppGUI_Test {

    private AppGUI appGUI;
    private JButton loginbutton;
    private JButton signupbutton;

    @BeforeEach
    void setUp() {
        appGUI = new AppGUI();
        loginbutton = getPrivateField("loginbutton");
        signupbutton = getPrivateField("signupbutton");
    }

    @Test
    void testUIComponents() {
        assertNotNull(loginbutton);
        assertNotNull(signupbutton);
        assertEquals("Holla...", appGUI.getTitle());
        assertEquals(1000, appGUI.getWidth());
        assertEquals(600, appGUI.getHeight());
    }

    @Test
    void testLoginButtonAction() {
        loginbutton.doClick();

        // Verify that LoginFrontend is created
        assertTrue(isFrameVisible(LoginFrontend.class));
        // Verify that the AppGUI is disposed
        assertFalse(appGUI.isVisible());
    }

    @Test
    void testSignupButtonAction() {
        signupbutton.doClick();

        // Verify that SignupFrontend is created
        assertTrue(isFrameVisible(SignupFrontend.class));
        // Verify that the AppGUI is disposed
        assertFalse(appGUI.isVisible());
    }

    // Helper methods to access private fields and methods using reflection
    private <T> T getPrivateField(String fieldName) {
        try {
            Field field = AppGUI.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(appGUI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isFrameVisible(Class<?> frameClass) {
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            if (frame.getClass() == frameClass && frame.isVisible()) {
                return true;
            }
        }
        return false;
    }
}
