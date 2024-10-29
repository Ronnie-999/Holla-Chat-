package de.buw.se;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;

// This class represents a custom JButton with a stadium-shaped background, specifically designed with a green tint

public class StadiumButtongreen extends JButton {
    // Default background color
    private Color backgroundColor = new Color(0, 0, 0); // Black
    // Default foreground color
    private Color foregroundColor = Color.white;

    // Constructor
    public StadiumButtongreen(String text) {
        super(text);
        // Set the button to be transparent
        setContentAreaFilled(false);
        // Set the foreground color
        setForeground(foregroundColor);
        // Set preferred size
        setPreferredSize(new Dimension(120, 40)); 
        // Increase font size by 2
        Font font = getFont();
        setFont(font.deriveFont(font.getSize() + 2f));
    }

    // Override the paintComponent method to draw the button
    @Override
    protected void paintComponent(Graphics g) {
        // Create Graphics2D object for advanced drawing
        Graphics2D g2d = (Graphics2D) g.create();
        // Enable anti-aliasing for smoother graphics
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get width and height of the button
        int width = getWidth();
        int height = getHeight();

        // Create a transparent color with the same RGB values as backgroundColor
        Color transparentColor = new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 150);

        // Set the fill color to the transparent color
        g2d.setColor(transparentColor);
        // Draw a rounded rectangle to represent the button background
        g2d.fillRoundRect(0, 0, width, height, height, height);

        // Call the super method to draw the button text and icon
        super.paintComponent(g);

        // Dispose of the Graphics2D object to free up resources
        g2d.dispose();
    }

    // Override the paintBorder method to remove the border
    @Override
    protected void paintBorder(Graphics g) {
        // Do nothing to remove the border
    }

    // Method to set the background color of the button
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    // Method to set the foreground color of the button
    public void setForegroundColor(Color color) {
        this.foregroundColor = color;
        // Set the foreground color of the button
        setForeground(color);
    }
}
