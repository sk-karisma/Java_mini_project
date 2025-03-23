//Coded by Meghna

import java.awt.*; // Importing Swing components for creating GUI elements.
import java.util.ArrayList; // Importing AWT classes for layout management and component settings.
import javax.swing.*; // Importing ArrayList class for dynamic array functionality.

public class LoginWindow extends CommonFunctions {
    // Method to display the login window for the movie booking system
    public static void showLoginWindow() {
        hideAllFrames(); // Hide any previously opened frames
        
        // Create a new JFrame for the login window
        JFrame loginFrame = new JFrame("Movie Booking System");
        addToFrameList(loginFrame); // Add this frame to the list of frames to manage visibility
        
        // Set the default close operation and size for the login window
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(350, 250);
        loginFrame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create a panel to hold the components of the login window
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Set layout to vertical box
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        // Create and configure labels and input fields
        JLabel usernameLabel = new JLabel("Enter username:");
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30)); // Set preferred size for username input
        usernameField.setMaximumSize(new Dimension(300, 30)); // Set maximum size for the input field

        // Create buttons for user and staff login
        JButton userButton = new JButton("User");
        JButton staffButton = new JButton("Staff");

        // Add components to the panel with spacing
        panel.add(usernameLabel);
        panel.add(Box.createVerticalStrut(5)); // Vertical spacing
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(15)); // Vertical spacing
        panel.add(userButton);
        panel.add(Box.createVerticalStrut(10)); // Vertical spacing
        panel.add(staffButton);

        // Align components to the center of the panel
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        userButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        staffButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Action listener for the user login button
        userButton.addActionListener(e -> {
            String username = usernameField.getText(); // Get the entered username
            // Check if the username is valid
            if (username != null && !username.trim().isEmpty()) {
                CommonFunctions.currentUser = username; // Set the current user
                // If the user has no bookings, initialize an empty list for them
                if (!CommonFunctions.userBookings.containsKey(username)) {
                    CommonFunctions.userBookings.put(username, new ArrayList<>());
                }
                User.showUserMainMenu(); // Show the main menu for the user
            } else {
                // Show a message dialog if the username is empty
                JOptionPane.showMessageDialog(loginFrame, "Please enter a username");
            }
        });

        // Action listener for the staff login button
        staffButton.addActionListener(e -> {
            // Prompt for the staff password
            String password = JOptionPane.showInputDialog(loginFrame, "Enter staff password:");
            // Validate the entered password
            if (password != null && password.equals("admin123")) {
                Staff.showStaffMainMenu(); // Show the main menu for staff
            } else {
                // Show a message dialog if the password is invalid
                JOptionPane.showMessageDialog(loginFrame, "Invalid password");
            }
        });

        // Add the panel to the login frame and make it visible
        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }
}
