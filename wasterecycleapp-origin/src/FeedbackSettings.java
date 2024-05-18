import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class FeedbackSettings {

    private JFrame frame;
    private JTextArea feedbackTextArea;
    private static final String JDBC_URL = "\"jdbc:mysql://localhost:3306/wasterecycledb?serverTimezone=UTC\", \"root\", \"root\"";

	public FeedbackSettings() {
		// TODO Auto-generated constructor stub
		 try {
	            // Use Nimbus Look and Feel for a modern appearance
	            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
	            e.printStackTrace();
	        }
		 
		// Create the main frame
	        frame = new JFrame("Feedback and Support");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(400, 250);
	        frame.setLayout(new BorderLayout());
	        
	        

	        // Set background color to light green
	        frame.getContentPane().setBackground(new Color(144, 238, 144));

	        // Create and add a panel for components
	        JPanel panel = new JPanel(new GridBagLayout());
	        panel.setBackground(new Color(144, 238, 144)); // Light green background
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(10, 10, 10, 10);

	        // Feedback label and text area
	        JLabel feedbackLabel = new JLabel("Feedback:");
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.anchor = GridBagConstraints.WEST;
	        panel.add(feedbackLabel, gbc);

	        feedbackTextArea = new JTextArea(5, 20);
	        feedbackTextArea.setLineWrap(true);
	        JScrollPane scrollPane = new JScrollPane(feedbackTextArea);
	        gbc.gridx = 1;
	        gbc.gridy = 0;
	        gbc.fill = GridBagConstraints.BOTH;
	        panel.add(scrollPane, gbc);

	        // Submit button
	        JButton submitButton = new JButton("Submit Feedback");
	        submitButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                submitFeedback();
	            }
	        });
	        gbc.gridx = 1;
	        gbc.gridy = 1;
	        panel.add(submitButton, gbc);

	        // View Feedback button
	        JButton viewFeedbackButton = new JButton("View Feedback");
	        viewFeedbackButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                openViewFeedback();
	            }
	        });
	        gbc.gridx = 1;
	        gbc.gridy = 2;
	        panel.add(viewFeedbackButton, gbc);

	        // Help button
	        JButton helpButton = new JButton("Ask for Help");
	        helpButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                askForHelp();
	            }
	        });
	        gbc.gridx = 1;
	        gbc.gridy = 3;
	        panel.add(helpButton, gbc);

	        // Add the panel to the frame
	        frame.getContentPane().add(panel, BorderLayout.CENTER);

	        // Set the frame to be visible
	        frame.setVisible(true);
	    }

	    private void submitFeedback() {
	        String feedback = feedbackTextArea.getText();

	        // Assuming you have a method to get the current user's ID
	        int userId = getCurrentUserId();

	        // Get the current timestamp
	        String submissionDate = getCurrentTimestamp();

	        // Store feedback in the database
	        storeFeedbackInDatabase(userId, feedback, submissionDate);

	        // Display a message dialog
	        JOptionPane.showMessageDialog(frame, "Feedback submitted:\n" + feedback);
	    }

	    private int getCurrentUserId() {
	        // Implement logic to get the current user's ID
	        // For demonstration purposes, returning a constant value
	        return 1;
	    }

	    private String getCurrentTimestamp() {
	        // Get the current timestamp in the format "yyyy-MM-dd HH:mm:ss"
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        return sdf.format(new Date());
	    }

	    private void storeFeedbackInDatabase(int userId, String feedback, String submissionDate) {
	        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
	            // Insert data into the feedback table
	            String insertQuery = "INSERT INTO feedback (user_id, feedback_text, submission_date) VALUES (?, ?, ?)";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
	                preparedStatement.setInt(1, userId);
	                preparedStatement.setString(2, feedback);
	                preparedStatement.setString(3, submissionDate);
	                preparedStatement.executeUpdate();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(frame, "Error submitting feedback. See console for details.");
	        }
	    }

	    private void openViewFeedback() {
	        new ViewFeedback();
	    }

	    private void askForHelp() {
	        // Display a dialog with information on how to ask for help from the admin
	        String helpMessage = "To ask for help from the admin, please send an email to admin@example.com.";
	        JOptionPane.showMessageDialog(frame, helpMessage, "Ask for Help", JOptionPane.INFORMATION_MESSAGE);
	    }

	
	
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FeedbackSettings();
            }
        });
    }

}
