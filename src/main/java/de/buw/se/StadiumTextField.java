package de.buw.se;

import javax.swing.*;
import java.awt.*;

// This class represents a custom JTextField with a stadium-shaped background

public class StadiumTextField extends JTextField {

    // Character used for echoing
    private char echoChar = '\0';

    // Constructor
    public StadiumTextField(int columns) {
        super(columns);
        // Make the text field transparent
        setOpaque(false);
    }

    // Override the paintComponent method to draw the background and text
    @Override
    protected void paintComponent(Graphics g) {
        // Create Graphics2D object for advanced drawing
        Graphics2D g2d = (Graphics2D) g.create();
        // Enable anti-aliasing for smoother graphics
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get width and height of the text field
        int width = getWidth();
        int height = getHeight();

        // Set the fill color to the background color
        g2d.setColor(getBackground());
        // Draw a rounded rectangle to represent the text field background
        g2d.fillRoundRect(0, 0, width - 1, height - 1, height, height);

        // Set the color to the foreground color
        g2d.setColor(getForeground());

        // Check if echo character is set
        if (getEchoChar() != '\0') {
            char[] echoChars = new char[getText().length()];
            // Fill the echoChars array with the echoChar
            for (int i = 0; i < echoChars.length; i++) {
                echoChars[i] = getEchoChar();
            }
            // Create a string from the echoChars array
            String echoText = new String(echoChars);
            // Draw the echo text at position (4, (height + ascent) / 2)
            g2d.drawString(echoText, 4, (height + g2d.getFontMetrics().getAscent()) / 2);
        } else {
            // If no echo character is set, call the super method to draw the text
            super.paintComponent(g);
        }

        // Dispose of the Graphics2D object to free up resources
        g2d.dispose();
    }

    // Method to set the echo character
    public void setEchoChar(char c) {
        this.echoChar = c;
        // Repaint the text field
        repaint();
    }

    // Method to get the echo character
    public char getEchoChar() {
        return echoChar;
    }
}
