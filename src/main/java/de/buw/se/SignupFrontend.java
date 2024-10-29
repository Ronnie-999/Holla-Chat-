package de.buw.se;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import de.buw.se.User;
//import de.buw.se.UserRegistration;
//import de.buw.se.StadiumButton;
//import de.buw.se.StadiumTextField;

public class SignupFrontend extends JFrame implements ActionListener 
{
    // User input fields and signup button
    private StadiumTextField  nameField, usernameField, passwordField, emailField;
    private JButton signup;

    
     //Constructor for the RegistrationPage class.
     
    public SignupFrontend() 
    {
        // Setting up the frame
        setTitle("SignUp Page");
        setSize(1000, 600);

        // Creating the signup page with a background image
        JPanel signupwindow = new JPanel(new GridBagLayout())
        {
            // Painting the background image
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                ImageIcon signup_icon = new ImageIcon("regBG.jpg");
                Image Signupbg = signup_icon.getImage();
                g.drawImage(Signupbg, 0, 0, getWidth(), getHeight(), this); 
            }
        };
        
        // Initializing the user input fields
        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(20, 20, 20, 20);
        
        // Name label
        grid.gridx = 0;
        grid.gridy = 0;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.BLACK);
        signupwindow.add(nameLabel, grid);
        Font font = nameLabel.getFont();
        nameLabel.setFont(new Font(font.getName(), Font.ITALIC, 16));

        // Name field
        grid.gridx = 1;
        grid.gridy = 0;
        nameField = new StadiumTextField(20);
        nameField.setBackground(Color.BLACK);
        nameField.setForeground(Color.WHITE);
        nameField.setPreferredSize(new Dimension(300, 30));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBorder(null);
        signupwindow.add(nameField, grid);

        // Username label
        grid.gridx = 0;
        grid.gridy = 1;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.BLACK);
        signupwindow.add(usernameLabel, grid);
        Font font1 = usernameLabel.getFont();
        usernameLabel.setFont(new Font(font.getName(), Font.ITALIC, 16));

        // Username field
        grid.gridx = 1;
        grid.gridy = 1;
        usernameField = new StadiumTextField(20);
        usernameField.setBackground(Color.BLACK);
        usernameField.setForeground(Color.WHITE);
        usernameField.setPreferredSize(new Dimension(300, 30));
        usernameField.setHorizontalAlignment(JTextField.CENTER);
        usernameField.setBorder(null);
        signupwindow.add(usernameField, grid);

        // Password label
        grid.gridx = 0;
        grid.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.BLACK);
        signupwindow.add(passwordLabel, grid);
        Font font2 = passwordLabel.getFont();
        passwordLabel.setFont(new Font(font.getName(), Font.ITALIC, 16));

        // Password field
        grid.gridx = 1;
        grid.gridy = 2;
        passwordField = new StadiumTextField(20);
        passwordField.setBackground(Color.BLACK);
        passwordField.setForeground(Color.WHITE);
        passwordField.setPreferredSize(new Dimension(300, 30));
        passwordField.setEchoChar('*');
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setBorder(null);
        signupwindow.add(passwordField, grid);

        // Email label
        grid.gridx = 0;
        grid.gridy = 3;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.BLACK); 
        signupwindow.add(emailLabel, grid);
        Font font3 = emailLabel.getFont();
        emailLabel.setFont(new Font(font.getName(), Font.ITALIC, 16));

        // Email field
        grid.gridx = 1;
        grid.gridy = 3;
        emailField = new StadiumTextField(20);
        emailField.setBackground(Color.BLACK);
        emailField.setForeground(Color.WHITE);
        emailField.setPreferredSize(new Dimension(300, 30));
        emailField.setHorizontalAlignment(JTextField.CENTER);
        emailField.setBorder(null);
        signupwindow.add(emailField, grid);
        
        // Signup button
        grid.gridx = 0;
        grid.gridy = 4;
        grid.gridwidth = 2;
        signup = new StadiumButton("<html><div style='text-align: center;'>Register</div></html>");
        signup.addActionListener(this);
        signupwindow.add(signup, grid);
        add(signupwindow);
        
        // Centering the frame on the screen
        setLocationRelativeTo(null);
        setVisible(true);
        
        // Close window listener
        addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e) 
            {
                dispose();
            }
        });
    }

    
    //ActionListener implementation to handle button clicks.
    
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == signup) 
        {
            // Taking the user input
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();

            // Storing user input in a variable to pass to the backend class to store the data and open the login page
            if (SignupBackend.Signup(name, username, password, email)) 
            {
                System.out.println("Registration successful!");
                new LoginFrontend();
                dispose(); // Close the current registration page
            } else {
                System.out.println("Failed to register user.");
            }
        }
    }
}
