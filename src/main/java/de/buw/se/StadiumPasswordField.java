package de.buw.se;

import java.awt.*;
import javax.swing.*;

// This class represents a custom JPasswordField with a stadium-shaped background

public class StadiumPasswordField extends JPasswordField {

    // Constructor
    public StadiumPasswordField(int columns) {
        super(columns);
        // Make the password field transparent
        setOpaque(false);
    }

    // Override the paintComponent method to draw the background
    @Override
    protected void paintComponent(Graphics g) {
        // Create Graphics2D object for advanced drawing
        Graphics2D g2d = (Graphics2D) g.create();
        // Enable anti-aliasing for smoother graphics
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get width and height of the password field
        int width = getWidth();
        int height = getHeight();

        // Set the fill color to the background color
        g2d.setColor(getBackground());
        // Draw a rounded rectangle to represent the password field background
        g2d.fillRoundRect(0, 0, width - 1, height - 1, height, height);

        // Call the super method to draw the password characters
        super.paintComponent(g);

        // Dispose of the Graphics2D object to free up resources
        g2d.dispose();
    }
}
