package de.buw.se;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * Class for fetching data from the database and displaying it in a UI.
 */
public class AfterLogin extends JFrame {
    private ArrayList<JButton> buttons;
    private String recipient;

    /**
     * Constructor for FetchDataFromDatabase class.
     * @parameter sender The username of the sender.
     */
    public AfterLogin(String sender) {
        // Setting up the frame
        setTitle("Holla, " + sender + "!");
        setSize(1000, 800); // Increased size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating the panel for displaying user data
        JPanel landingpage = new JPanel(new GridLayout(0, 2, 10, 10)) { // Increased spacing
            // Painting the background image
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon afterlogin_Icon = new ImageIcon("afterloginBG.jpg");
                Image afterloginBG = afterlogin_Icon.getImage();
                g.drawImage(afterloginBG, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Setting up the layout for user data display
        buttons = new ArrayList<>();

        // Fetching usernames from the database
        getUsernames(sender);

        // Creating label for displaying username with padding at the top
        JLabel usernameLabel = new JLabel("Logged in as: " + sender);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Adding padding
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Adding username label to the frame
        getContentPane().add(usernameLabel, BorderLayout.NORTH);

        // Adding buttons for each fetched username
        for (JButton button : buttons) {
            landingpage.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Setting recipient when button is clicked
                    recipient = button.getText();
                    // Opening chat UI with sender and recipient
                    ChatFrontend chatfrontend = new ChatFrontend(sender, recipient);
                    chatfrontend.setVisible(true);
                }
            });
        }
        
        // Adding user data panel to a scrollable pane with increased size
        JScrollPane scrollPane = new JScrollPane(landingpage);
        scrollPane.setPreferredSize(new Dimension(900, 600)); // Increased size
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Setting up the search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Text field for search
        JTextField searchField = new JTextField(30);
        searchField.setPreferredSize(new Dimension(250, 30));
        searchField.setHorizontalAlignment(JTextField.CENTER);
        searchField.setBorder(null);

        // Button for search
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setBorder(null);
        searchButton.setBackground(new Color(181, 229, 80));
        Font fonts1 = searchButton.getFont();
        searchButton.setFont(new Font(fonts1.getName(), Font.BOLD, 16));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Getting the search text
                String searchText = searchField.getText();
                // Perform search operation
                searchInDatabase(searchText, sender);
            }
        });
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Button for edit profile
        JButton editProfileButton = new JButton("Edit Profile");
        editProfileButton.setPreferredSize(new Dimension(150, 30));
        editProfileButton.setBorder(null);
        editProfileButton.setBackground(new Color(181, 229, 80));
        Font editProfileFont = editProfileButton.getFont();
        editProfileButton.setFont(new Font(editProfileFont.getName(), Font.BOLD, 16));
        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open prompt for editing profile
                openEditProfilePrompt(sender);
            }
        });
        searchPanel.add(editProfileButton);

        // Button for group message
        JButton groupMessageButton = new JButton("Group Message");
        groupMessageButton.setPreferredSize(new Dimension(150, 30));
        groupMessageButton.setBorder(null);
        groupMessageButton.setBackground(new Color(181, 229, 80));
        Font groupMessageFont = groupMessageButton.getFont();
        groupMessageButton.setFont(new Font(groupMessageFont.getName(), Font.BOLD, 16));
        groupMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open prompt for selecting recipients for group message
                openGroupMessagePrompt(sender);
            }
        });
        searchPanel.add(groupMessageButton);

        // Button for logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(100, 30));
        logoutButton.setBorder(null);
        logoutButton.setBackground(new Color(181, 229, 80));
        Font logoutFont = logoutButton.getFont();
        logoutButton.setFont(new Font(logoutFont.getName(), Font.BOLD, 16));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window and open the login page
                dispose();
                new LoginFrontend();
            }
        });
        searchPanel.add(logoutButton);

        // Adding search panel to the frame
        getContentPane().add(searchPanel, BorderLayout.NORTH);

        // Setting frame visibility and resizable properties
        setVisible(true);
        setResizable(false);
    }

    /**
     * Fetches usernames from the database and adds corresponding buttons to the UI.
     * @parameter sender The username of the sender.
     */
    private void getUsernames(String sender) {
        try {
            // Getting the SQL statement from the database connection
            Statement sql = DataBase.getSqlStatement();
            // Constructing the SQL query to fetch usernames
            String query = "SELECT user_name FROM USERS1 where user_name not in ('" + sender + "')";
            try (ResultSet resultSet = sql.executeQuery(query)) {
                while (resultSet.next()) {
                    // Getting username from the result set
                    String username = resultSet.getString("user_name");
                    // Creating button for the username
                    JButton button = new JButton(username);
                    buttons.add(button); // Adding button to the list
                }
                sql.close(); // Closing the SQL statement
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches the database based on the provided search text.
     * @parameter searchText The text to be searched.
     * @parameter sender The username of the sender.
     */
    private void searchInDatabase(String searchText, String sender) {
        try {
            // Fetch search results from the backend
            ArrayList<String> searchResults = ChatBackend.searchMessages(sender, searchText);
            // Open the search results window
            SearchResultsWindow searchResultsWindow = new SearchResultsWindow(sender, searchResults);
            searchResultsWindow.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to open prompt for selecting recipients for group message.
     */
    private void openGroupMessagePrompt(String sender) {
        // Fetch usernames from the database
        ArrayList<String> usernames = new ArrayList<>();
        try {
            // Getting the SQL statement from the database connection
            Statement sql = DataBase.getSqlStatement();
            // Constructing the SQL query to fetch usernames
            String query = "SELECT user_name FROM USERS1 where user_name not in ('" + sender + "')";
            try (ResultSet resultSet = sql.executeQuery(query)) {
                while (resultSet.next()) {
                    // Getting username from the result set
                    String username = resultSet.getString("user_name");
                    usernames.add(username);
                }
                sql.close(); // Closing the SQL statement
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create and configure the dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Group Message");
        dialog.setSize(500, 300); // Adjusted size
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());

        // Checkbox panel with scrolling
        JScrollPane checkboxScrollPane = new JScrollPane();
        JPanel checkboxPanel = new JPanel(new GridLayout(0, 1));
        for (String username : usernames) {
            JCheckBox checkBox = new JCheckBox(username);
            checkboxPanel.add(checkBox);
        }
        checkboxScrollPane.setViewportView(checkboxPanel);
        dialog.add(checkboxScrollPane, BorderLayout.CENTER);

        // Message input
        JTextField messageField = new JTextField();
        dialog.add(messageField, BorderLayout.SOUTH);

        // Send button
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get selected recipients
                ArrayList<String> recipients = new ArrayList<>();
                Component[] components = checkboxPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) component;
                        if (checkBox.isSelected()) {
                            recipients.add(checkBox.getText());
                        }
                    }
                }
                // Get message
                String message = messageField.getText();
                // Send message to selected recipients
                for (String recipient : recipients) {
                    ChatBackend.storeMessage(sender, recipient, message);
                }
                dialog.dispose(); // Close dialog
            }
        });
        dialog.add(sendButton, BorderLayout.EAST);

        // Make dialog visible
        dialog.setVisible(true);
    }

    /**
     * Method to open prompt for editing profile.
     */
    private void openEditProfilePrompt(String sender) {
        // Create and configure the dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Edit Profile");
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new GridLayout(0, 1));

        // Text field for username change
        JTextField usernameField = new JTextField();
        dialog.add(usernameField);

        // Button for changing username
        JButton changeUsernameButton = new JButton("Change Username");
        changeUsernameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to change username
                String newUsername = usernameField.getText();
                // Call method to change username
                changeUsername(sender, newUsername);
                // Close dialog
                dialog.dispose();
            }
        });
        dialog.add(changeUsernameButton);

        // Text field for password change
        JTextField passwordField = new JTextField();
        dialog.add(passwordField);

        // Button for changing password
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to change password
                String newPassword = passwordField.getText();
                // Call method to change password
                changePassword(sender, newPassword);
                // Close dialog
                dialog.dispose();
            }
        });
        dialog.add(changePasswordButton);

        // Make dialog visible
        dialog.setVisible(true);
    }

    /**
     * Method to change username.
     */
    private void changeUsername(String sender, String newUsername) {
        DataBase.updateUsername(sender, newUsername);
    }

    /**
     * Method to change password.
     */
    private void changePassword(String sender, String newPassword) {
        DataBase.updatePassword(sender, newPassword);
    }

    public static void main(String[] args) {
        // Example usage
        SwingUtilities.invokeLater(() -> new AfterLogin("sender"));
    }

    public void actionPerformed(ActionEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}

