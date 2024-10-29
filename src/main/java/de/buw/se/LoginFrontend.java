package de.buw.se;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginFrontend extends JFrame implements ActionListener {
    private JButton login;	
    private StadiumTextField usernameField;
    private StadiumPasswordField passwordField; 

    /**
     * Constructor for the LoginPage class.
     */
    public LoginFrontend() {
        // Setting up the frame
        setTitle("Login Page");
        setSize(1000, 600);
        
        // Creating panel for login page with background image
        JPanel loginpage = new JPanel(new GridBagLayout()) {
            // Painting the background image
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon login_icon = new ImageIcon("login_page1.jpg");
                Image loginbg = login_icon.getImage();
                g.drawImage(loginbg, 0, 0, getWidth(), getHeight(), this); 
            }
        };
        
        // Initializing the user inputs
        GridBagConstraints feilds = new GridBagConstraints();
        feilds.insets = new Insets(20, 20, 20, 20);
        
        // Username label
        feilds.gridx = 0;
        feilds.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        loginpage.add(usernameLabel, feilds);
        Font font = usernameLabel.getFont();
        usernameLabel.setFont(new Font(font.getName(), Font.ITALIC, 16));

        // Username field
        feilds.gridx = 1;
        feilds.gridy = 0;
        usernameField = new StadiumTextField(20);
        usernameField.setPreferredSize(new Dimension(300, 30));
        usernameField.setBorder(null);
        usernameField.setHorizontalAlignment(JTextField.CENTER);
        loginpage.add(usernameField, feilds);

        // Password label
        feilds.gridx = 0;
        feilds.gridy = 1;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        loginpage.add(passwordLabel, feilds);
        Font fonts = passwordLabel.getFont();
        passwordLabel.setFont(new Font(fonts.getName(), Font.ITALIC, 16));

        // Password field
        feilds.gridx = 1;
        feilds.gridy = 1;
        passwordField = new StadiumPasswordField(20);
        passwordField.setPreferredSize(new Dimension(300, 30));
        passwordField.setBorder(null);
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        loginpage.add(passwordField, feilds);

        // Login button
        feilds.gridx = 0;
        feilds.gridy = 2;
        feilds.gridwidth = 2;
        login = new StadiumButton("Login");
        login.addActionListener(this);
        Font fonts1 = login.getFont();
        login.setFont(new Font(fonts1.getName(), Font.BOLD, 16));
        loginpage.add(login , feilds);
        add(loginpage);

        // Center the frame on the screen
        setLocationRelativeTo(null);
        setVisible(true);

        // Close window listener
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
      
    /**
     * ActionListener implementation to handle button clicks.
     */
    public void actionPerformed(ActionEvent e) {
        // Take the user input and handover to the backend to check the data validity from the database
        if (e.getSource() == login) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Validate username and password fields
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verify user credentials against stored information
            try {
                if (LoginBackend.authentication(username, password)) {
                    // If login is successful, close the login page and open data fetching page
                    dispose();
                    new AfterLogin(username);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid login credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (LoginBackend.EmptyCredentialsException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
