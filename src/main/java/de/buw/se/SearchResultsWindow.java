package de.buw.se;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SearchResultsWindow extends JFrame {

    public SearchResultsWindow(String sender, ArrayList<String> searchResults) {
        setTitle("Search Results");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (String result : searchResults) {
            JLabel label = new JLabel(result);
            panel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
