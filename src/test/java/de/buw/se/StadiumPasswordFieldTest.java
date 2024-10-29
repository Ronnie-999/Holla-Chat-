package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StadiumPasswordFieldTest {

    private StadiumPasswordField stadiumPasswordField;

    @BeforeEach
    void setUp() {
        stadiumPasswordField = new StadiumPasswordField(20);
    }

    @Test
    void testPasswordFieldInitialization() {
        // Check initial column count
        assertEquals(20, stadiumPasswordField.getColumns());

        // Check initial opaque setting
        assertFalse(stadiumPasswordField.isOpaque());
    }

    @Test
    void testSetBackground() {
        Color newColor = Color.RED;
        stadiumPasswordField.setBackground(newColor);
        assertEquals(newColor, stadiumPasswordField.getBackground());
    }

    @Test
    void testPaintComponent() {
        // Simulate painting the password field
        stadiumPasswordField.setSize(200, 40);
        stadiumPasswordField.setBackground(Color.GREEN);
        BufferedImage image = new BufferedImage(200, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        stadiumPasswordField.paintComponent(g2d);
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
    void testPasswordFieldInput() {
        // Set and check password input
        stadiumPasswordField.setText("password");
        assertEquals("password", new String(stadiumPasswordField.getPassword()));
    }
}

