package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Container;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SearchResultsWindowTest {

    private SearchResultsWindow searchResultsWindow;
    private ArrayList<String> searchResults;

    @BeforeEach
    void setUp() {
        searchResults = new ArrayList<>();
        searchResults.add("Result 1");
        searchResults.add("Result 2");
        searchResults.add("Result 3");

        searchResultsWindow = new SearchResultsWindow("sender", searchResults);
    }

    @Test
    void testWindowProperties() {
        assertEquals("Search Results", searchResultsWindow.getTitle());
        assertEquals(400, searchResultsWindow.getWidth());
        assertEquals(400, searchResultsWindow.getHeight());
        assertEquals(JFrame.DISPOSE_ON_CLOSE, searchResultsWindow.getDefaultCloseOperation());
    }

    @Test
    void testSearchResultsDisplay() {
        Container contentPane = searchResultsWindow.getContentPane();
        assertTrue(contentPane.getComponent(0) instanceof JScrollPane);

        JScrollPane scrollPane = (JScrollPane) contentPane.getComponent(0);
        JViewport viewport = scrollPane.getViewport();
        assertTrue(viewport.getView() instanceof JPanel);

        JPanel panel = (JPanel) viewport.getView();
        assertEquals(BoxLayout.Y_AXIS, ((BoxLayout) panel.getLayout()).getAxis());
        assertEquals(searchResults.size(), panel.getComponentCount());

        for (int i = 0; i < searchResults.size(); i++) {
            assertTrue(panel.getComponent(i) instanceof JLabel);
            JLabel label = (JLabel) panel.getComponent(i);
            assertEquals(searchResults.get(i), label.getText());
        }
    }

    @Test
    void testInitialVisibility() {
        assertFalse(searchResultsWindow.isVisible());
    }
}
