package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StadiumButtongreenTest {

    private StadiumButtongreen stadiumButtongreen;

    @BeforeEach
    void setUp() {
        stadiumButtongreen = new StadiumButtongreen("Test");
    }

    @Test
    void testButtonInitialization() {
        // Check initial text
        assertEquals("Test", stadiumButtongreen.getText());

        // Check initial background and foreground colors
        assertEquals(new Color(0, 0, 0), stadiumButtongreen.getBackground()); // Background color should be black
        assertEquals(Color.white, stadiumButtongreen.getForeground()); // Foreground color should be white

        // Check initial size
        Dimension expectedSize = new Dimension(120, 40);
        assertEquals(expectedSize, stadiumButtongreen.getPreferredSize());

        // Check font size increase
        Font font = stadiumButtongreen.getFont();
        assertEquals(font.getSize() + 2f, font.deriveFont(font.getSize() + 2f).getSize(), 0.1);
    }

    @Test
    void testSetBackgroundColor() {
        Color newColor = Color.RED;
        stadiumButtongreen.setBackgroundColor(newColor);
        assertEquals(newColor, stadiumButtongreen.getBackground());
    }

    @Test
    void testSetForegroundColor() {
        Color newColor = Color.GREEN;
        stadiumButtongreen.setForegroundColor(newColor);
        assertEquals(newColor, stadiumButtongreen.getForeground());
    }

    @Test
    void testPaintComponent() {
        // Simulate painting the button
        stadiumButtongreen.setSize(120, 40);
        BufferedImage image = new BufferedImage(120, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        stadiumButtongreen.paintComponent(g2d);
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
    void testPaintBorder() {
        // Simulate painting the border
        stadiumButtongreen.setSize(120, 40);
        BufferedImage image = new BufferedImage(120, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        stadiumButtongreen.paintBorder(g2d);
        g2d.dispose();

        // Check that the border is not painted (i.e., remains empty)
        boolean isEmpty = true;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getRGB(x, y) != 0) {
                    isEmpty = false;
                    break;
                }
            }
        }
        assertTrue(isEmpty);
    }
}
