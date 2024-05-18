import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewFeedback {

    private JFrame frame;
    private JTextArea feedbackDisplay;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/wasterecycledb?serverTimezone=UTC\"";

    public ViewFeedback() {
        try {
            // Use Nimbus Look and Feel for a modern appearance
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // Create the main frame
        frame = new JFrame("View Feedback");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Set background color to light green
        frame.getContentPane().setBackground(new Color(144, 238, 144));

        // Create and add a panel for components
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(173, 255, 173)); // Lighter green background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Feedback label and text area
        JLabel feedbackLabel = new JLabel("Feedback Entries:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(feedbackLabel, gbc);

        feedbackDisplay = new JTextArea(10, 30);
        feedbackDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(feedbackDisplay);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        // Add the panel to the frame
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        // Populate the feedback entries
        populateFeedbackEntries();

        // Set the frame to be visible
        frame.setVisible(true);
    }

    private void populateFeedbackEntries() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            String selectQuery = "SELECT * FROM feedback";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                    StringBuilder feedbackText = new StringBuilder();
                    while (resultSet.next()) {
                        int userId = resultSet.getInt("user_id");
                        String feedback = resultSet.getString("feedback_text");
                        String submissionDate = resultSet.getString("submission_date");

                        feedbackText.append("User ID: ").append(userId).append("\n");
                        feedbackText.append("Feedback: ").append(feedback).append("\n");
                        feedbackText.append("Submission Date: ").append(submissionDate).append("\n");
                        feedbackText.append("\n");
                    }
                    feedbackDisplay.setText(feedbackText.toString());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching feedback entries. See console for details.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ViewFeedback();
            }
        });
    }
}
