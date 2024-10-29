package de.buw.se;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ChatFrontend extends JFrame {

    private String sender;
    private String receiver;
    private JTextArea chatscreen;
    private JTextField messagebox;

    /**
     * Constructor for ChatFrontend class.
     *
     * @param sender   The username of the sender.
     * @param receiver The username of the receiver.
     */
    public ChatFrontend(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;

        // Setting up the frame
        setTitle("Chat with " + receiver);
        setSize(600, 400);

        // Creating the chat window
        JPanel chatwindow = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Setting background image (if needed)
                // ImageIcon chat_icon = new ImageIcon("login_page1.jpg");
                // Image chatBG = chat_icon.getImage();
                // g.drawImage(chatBG, 0, 0, getWidth(), getHeight(), this);
            }
        };

        setContentPane(chatwindow);
        setVisible(true);

        // Setting up chat screen
        chatscreen = new JTextArea(10, 30);
        chatscreen.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatscreen);

        // Setting up emoji buttons (if needed)
        JPanel emojiPanel = new JPanel();
        emojiPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // Emoji buttons (if needed)
        JButton emoji1 = new JButton("\uD83D\uDE00"); // Grinning face
        emoji1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messagebox.setText(messagebox.getText() + "\uD83D\uDE00");
            }
        });
        emojiPanel.add(emoji1);

        JButton emoji2 = new JButton("\uD83D\uDE01"); // Grinning face with smiling eyes
        emoji2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messagebox.setText(messagebox.getText() + "\uD83D\uDE01");
            }
        });
        emojiPanel.add(emoji2);

        JButton emoji3 = new JButton("\uD83D\uDE02"); // Face with tears of joy
        emoji3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messagebox.setText(messagebox.getText() + "\uD83D\uDE02");
            }
        });
        emojiPanel.add(emoji3);

        // Setting up message area
        messagebox = new JTextField(30);
        messagebox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(messagebox.getText());
            }
        });

        // Setting up send button for the message box
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(messagebox.getText());
            }
        });

        // Setting up input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(emojiPanel, BorderLayout.NORTH); // Add emoji panel above message box
        inputPanel.add(messagebox, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST); // Add send button for message box
        chatwindow.add(scrollPane, BorderLayout.CENTER);
        chatwindow.add(inputPanel, BorderLayout.SOUTH);

        // Minimize button
        JButton minimizeButton = new JButton("-");
        minimizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setState(JFrame.ICONIFIED); // Minimize the frame
            }
        });
        chatwindow.add(minimizeButton, BorderLayout.NORTH);

        // Maximize button (optional)
        JButton maximizeButton = new JButton("+");
        maximizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Toggle maximize state
                if (getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
                } else {
                    setExtendedState(JFrame.NORMAL); // Restore the frame to normal size
                }
            }
        });
        chatwindow.add(maximizeButton, BorderLayout.NORTH);

        // Close window listener
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Start a thread to regularly refresh messages
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    refreshMessages();
                    try {
                        Thread.sleep(500); // Refresh every .5 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * Method to send a message.
     */
    private void sendMessage(String message) {
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cannot send empty message", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                // Save message in the database
                ChatBackend.storeMessage(sender, receiver, message);
                messagebox.setText(""); // Clear message field
                refreshMessages(); // Refresh messages to display the new message
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Failed to send message: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Method to refresh messages in the chat area.
     */
    private void refreshMessages() {
        try {
            // Get messages from the database
            List<String> messages = ChatBackend.fetchMessages(sender, receiver);
            chatscreen.setText(""); // Clear chat area
            // Append each message to the chat area
            for (String message : messages) {
                chatscreen.append(message + "\n");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to fetch messages: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Example usage: Create ChatFrontend with sender "Alice" and receiver "Bob"
            new ChatFrontend("Alice", "Bob");
        });
    }
}
