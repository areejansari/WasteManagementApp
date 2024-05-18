
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPage {

    private JFrame frame;

    public SettingsPage() {
        // Create the main frame
        frame = new JFrame("Settings Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        // Create and add a panel for components
        JPanel panel = new JPanel();
        panel.setBackground(new Color(144, 238, 144)); // Light green background
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Button color for all buttons (lighter green)
        Color buttonColor = new Color(173, 255, 173); // Lighter green color

        // Feedback and Support button
        JButton feedbackButton = new JButton("Feedback and Support");
        feedbackButton.setBackground(buttonColor);
        feedbackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFeedbackSettings();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(feedbackButton, gbc);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(buttonColor);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(logoutButton, gbc);

        // View Profile button
        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.setBackground(buttonColor);
        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserProfile();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(viewProfileButton, gbc);

        // Add the panel to the frame
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        // Set the frame to be visible
        frame.setVisible(true);
    }

    private void openFeedbackSettings() {
        // Redirect to the FeedbackSettings class
        frame.dispose(); // Close the current frame
        new FeedbackSettings();
    }

    private void logout() {
        // Redirect to the Logout class or perform logout actions
        frame.dispose(); // Close the current frame
        // Add logout logic here
    }

    private void openUserProfile() {
        // Prompt the user to enter their ID
        String idString = JOptionPane.showInputDialog(frame, "Enter your ID:");
        
        // Check if the user canceled the input
        if (idString == null) {
            return;
        }

        try {
            // Convert the entered ID to an integer
            int id = Integer.parseInt(idString);

            // Create and show the user profile window with the entered ID
            new UserProfileWindow(id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SettingsPage();
            }
        });
    }
}
