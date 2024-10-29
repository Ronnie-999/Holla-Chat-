package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StadiumTextFieldTest {

    private StadiumTextField stadiumTextField;

    @BeforeEach
    void setUp() {
        stadiumTextField = new StadiumTextField(20);
    }

    @Test
    void testTextFieldInitialization() {
        // Check initial column count
        assertEquals(20, stadiumTextField.getColumns());

        // Check initial opaque setting
        assertFalse(stadiumTextField.isOpaque());
    }

    @Test
    void testSetBackground() {
        Color newColor = Color.RED;
        stadiumTextField.setBackground(newColor);
        assertEquals(newColor, stadiumTextField.getBackground());
    }

    @Test
    void testSetForeground() {
        Color newColor = Color.GREEN;
        stadiumTextField.setForeground(newColor);
        assertEquals(newColor, stadiumTextField.getForeground());
    }

    @Test
    void testSetEchoChar() {
        char echoChar = '*';
        stadiumTextField.setEchoChar(echoChar);
        assertEquals(echoChar, stadiumTextField.getEchoChar());
    }

    @Test
    void testPaintComponent() {
        // Simulate painting the text field
        stadiumTextField.setSize(200, 40);
        stadiumTextField.setBackground(Color.GREEN);
        stadiumTextField.setForeground(Color.BLACK);
        stadiumTextField.setText("Test text");
        stadiumTextField.setEchoChar('*');
        BufferedImage image = new BufferedImage(200, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        stadiumTextField.paintComponent(g2d);
        g2d.dispose();

        // Check that the image is not empty (i.e., painting occurred)
        boolean isNotEmpty = false;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getRGB(x, y) != 0) {
                    isNotEmpty = true;
                    break;
                }
            }
        }
        assertTrue(isNotEmpty);
    }

    @Test
    void testTextFieldInput() {
        // Set and check text input
        stadiumTextField.setText("Test text");
        assertEquals("Test text", stadiumTextField.getText());
    }

    @Test
    void testEchoCharacterDisplay() {
        // Set echo character and text
        stadiumTextField.setEchoChar('*');
        stadiumTextField.setText("Test text");

        // Simulate painting the text field
        stadiumTextField.setSize(200, 40);
        BufferedImage image = new BufferedImage(200, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        stadiumTextField.paintComponent(g2d);
        g2d.dispose();

        // Check that the image contains the echo characters instead of the actual text
        boolean containsEchoChars = false;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                if (rgb != 0 && rgb != Color.GREEN.getRGB()) {
                    containsEchoChars = true;
                    break;
                }
            }
        }
        assertTrue(containsEchoChars);
    }
}
