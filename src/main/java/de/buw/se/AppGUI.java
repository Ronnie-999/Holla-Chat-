package de.buw.se;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class AppGUI extends JFrame implements ActionListener 
{
    private JButton loginbutton, signupbutton;

    //Constructor for the MainFrame class.
    
    public AppGUI() 
    {
        // Setting up the window
        setTitle("Holla...");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Creating the panel for the mainframe with background image
        JPanel mainframe = new JPanel() 
        {
            @Override
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                // Loading and drawing the background image
                ImageIcon mainframe_icon = new ImageIcon("mainframeBG.jpg");
                Image mainframe_bg = mainframe_icon.getImage();
                g.drawImage(mainframe_bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainframe.setLayout(new BorderLayout());
        setContentPane(mainframe);
        
        // Creating login and signup buttons
        loginbutton = new StadiumButton("Login");
        loginbutton.setForeground(Color.WHITE);
        loginbutton.setBackground(Color.BLACK);
        loginbutton.addActionListener(this);

        signupbutton = new StadiumButton("Signup");
        signupbutton.setForeground(Color.WHITE);
        signupbutton.setBackground(Color.BLACK);
        signupbutton.addActionListener(this);

        // Creating panels for login and signup buttons
        JPanel loginbox = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginbox.setOpaque(false);
        loginbox.add(Box.createVerticalGlue());
        loginbox.add(Box.createVerticalStrut(70));
        loginbox.add(loginbutton);

        JPanel signupbox = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signupbox.setOpaque(false);
        signupbox.add(Box.createVerticalGlue());
        signupbox.add(Box.createVerticalStrut(110));
        signupbox.add(signupbutton);

        // Adding login and signup panels to the main frame
        mainframe.add(loginbox, BorderLayout.SOUTH);
        mainframe.add(signupbox, BorderLayout.NORTH);

        setVisible(true);
    }

    //ActionListener implementation to handle button clicks.
     
    public void actionPerformed(ActionEvent e) 
    {
        DataBase.getSqlStatement();
        if (e.getSource() == loginbutton) 
        {
            // When login button is clicked, close current window and open login page
            dispose();
            LoginFrontend loginfrontend = new LoginFrontend();
        } 
        else if (e.getSource() == signupbutton) 
        {
            // When signup button is clicked, close current window and open signup page
            dispose();
            SignupFrontend signupfrontend = new SignupFrontend();
        }
    }

    /**
     * Entry point of the application.
     */
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(AppGUI::new);
    }
}
