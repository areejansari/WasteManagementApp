import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfileWindow {

    private JFrame frame;
    private JLabel userIdLabel;
    private JLabel userNameLabel;
    private JLabel emailLabel;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/wasterecycledb?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public UserProfileWindow(int userId) {
        try {
            // Use Nimbus Look and Feel for a modern appearance
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // Create the main frame
        frame = new JFrame("User Profile");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        // Create and add a panel for components
        JPanel panel = new JPanel();
        panel.setBackground(new Color(144, 238, 144)); // Light green background
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Fetch user information from the database
        UserInfo userInfo = getUserInfo(userId);

        // Display user information
        userIdLabel = new JLabel("User ID: " + userInfo.getId());
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userIdLabel, gbc);

        userNameLabel = new JLabel("Username: " + userInfo.getUserName());
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(userNameLabel, gbc);

        emailLabel = new JLabel("Email: " + userInfo.getEmail());
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(emailLabel, gbc);

        // Add the panel to the frame
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        // Set the frame to be visible
        frame.setVisible(true);
    }

    private UserInfo getUserInfo(int userId) {
        UserInfo userInfo = new UserInfo();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String selectQuery = "SELECT * FROM users WHERE id=3";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        userInfo.setId(resultSet.getInt("id"));
                        userInfo.setUserName(resultSet.getString("username"));
                        userInfo.setEmail(resultSet.getString("email"));
                        // You can add more fields as needed
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching user information. See console for details.");
        }

        return userInfo;
    }

    public static void main(String[] args) {
        // Provide the user ID when creating the UserProfileWindow
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserProfileWindow(1); // Replace 1 with the actual user ID
            }
        });
    }
}

class UserInfo {
    private int id;
    private String userName;
    private String email;
    // Add more fields as needed

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
