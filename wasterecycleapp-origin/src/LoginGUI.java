import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginGUI extends JFrame implements ActionListener, ItemListener {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel loginPanel;
    private JPanel registrationPanel;
    private JTextField usernameFieldReg;
    private JPasswordField passwordFieldReg;
    private JTextField emailFieldReg;
    private JButton registerButton;
    private JTextField usernameFieldLogin;
    private JPasswordField passwordFieldLogin;
    private JButton loginButton;
    private JButton registerLink;
    private JButton loginLink;
    private JCheckBox showPasswordCheckBox; // CheckBox to toggle password visibility
    private UserModel userModel;

    public LoginGUI() {
        userModel = new UserModel();

        setTitle("Recycle Waste System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1000, 800); // Set the width and height of the JFrame
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame to fullscreen
        setUndecorated(true); // Remove window decorations (title bar)

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(new Color(144, 238, 144)); // Light green background color

        loginPanel = createLoginPanel();
        registrationPanel = createRegistrationPanel();

        cardPanel.add(loginPanel, "LoginPanel");
        cardPanel.add(registrationPanel, "RegistrationPanel");

        add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, "LoginPanel");

        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(new Color(144, 238, 144)); // Light green background color

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(144, 238, 144)); // Light green background color

        // Login Section
        JLabel loginLabel = new JLabel("Welcome to Recycle Waste System");
        loginLabel.setFont(new Font("SansSerif", Font.BOLD, 40)); // Increased font size
        formPanel.add(loginLabel, getConstraints(0, 0, 2, 1));

        usernameFieldLogin = new JTextField(30); // Increased size
        passwordFieldLogin = new JPasswordField(30); // Increased size

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setFont(new Font("SansSerif", Font.PLAIN, 20)); // Increased font size
        loginButton.setBackground(new Color(0, 100, 0)); // Dark green
        loginButton.setForeground(Color.WHITE); // Set text color to white

        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBackground(new Color(144, 238, 144)); // Light green background color
        showPasswordCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 18)); // Increased font size
        showPasswordCheckBox.addItemListener(this);

        registerLink = new JButton("Register");
        registerLink.setForeground(new Color(0, 100, 0)); // Dark green
        registerLink.setBorderPainted(false);
        registerLink.setContentAreaFilled(false);
        registerLink.setFont(new Font("SansSerif", Font.PLAIN, 18)); // Increased font size
        registerLink.addActionListener(e -> cardLayout.show(cardPanel, "RegistrationPanel"));

        formPanel.add(createLabel("Username:"), getConstraints(0, 1, 1, 1));
        formPanel.add(usernameFieldLogin, getConstraints(1, 1, 1, 1));
        formPanel.add(createLabel("Password:"), getConstraints(0, 2, 1, 1));
        formPanel.add(passwordFieldLogin, getConstraints(1, 2, 1, 1));
        formPanel.add(showPasswordCheckBox, getConstraints(0, 3, 2, 1));
        formPanel.add(loginButton, getConstraints(0, 4, 2, 1));
        formPanel.add(registerLink, getConstraints(0, 5, 2, 1));

        loginPanel.add(formPanel, BorderLayout.CENTER);

        return loginPanel;
    }

    private JPanel createRegistrationPanel() {
        JPanel registrationPanel = new JPanel(new BorderLayout());
        registrationPanel.setBackground(new Color(144, 238, 144)); // Light green background color

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(144, 238, 144)); // Light green background color

        // Registration Section
        JLabel registrationLabel = new JLabel("Welcome to Recycle Waste System");
        registrationLabel.setFont(new Font("SansSerif", Font.BOLD, 40)); // Increased font size
        formPanel.add(registrationLabel, getConstraints(0, 0, 2, 1));

        usernameFieldReg = new JTextField(30); // Increased size
        passwordFieldReg = new JPasswordField(30); // Increased size
        emailFieldReg = new JTextField(30); // Increased size

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        registerButton.setFont(new Font("SansSerif", Font.PLAIN, 20)); // Increased font size
        registerButton.setBackground(new Color(0, 100, 0)); // Dark green
        registerButton.setForeground(Color.WHITE); // Set text color to white

        loginLink = new JButton("Login");
        loginLink.setForeground(new Color(0, 100, 0)); // Dark green
        loginLink.setBorderPainted(false);
        loginLink.setContentAreaFilled(false);
        loginLink.setFont(new Font("SansSerif", Font.PLAIN, 18)); // Increased font size
        loginLink.addActionListener(e -> cardLayout.show(cardPanel, "LoginPanel"));

        formPanel.add(createLabel("Username:"), getConstraints(0, 1, 1, 1));
        formPanel.add(usernameFieldReg, getConstraints(1, 1, 1, 1));
        formPanel.add(createLabel("Password:"), getConstraints(0, 2, 1, 1));
        formPanel.add(passwordFieldReg, getConstraints(1, 2, 1, 1));
        formPanel.add(createLabel("Email:"), getConstraints(0, 3, 1, 1));
        formPanel.add(emailFieldReg, getConstraints(1, 3, 1, 1));
        formPanel.add(registerButton, getConstraints(0, 4, 2, 1));
        formPanel.add(loginLink, getConstraints(0, 5, 2, 1));

        registrationPanel.add(formPanel, BorderLayout.CENTER);

        return registrationPanel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 20)); // Increased font size
        return label;
    }

    private GridBagConstraints getConstraints(int gridx, int gridy, int gridwidth, int gridheight) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.insets = new Insets(10, 10, 10, 10); // Increased insets
        return constraints;
    }

    private boolean isValidUsername(String username) {
        // Allow only alphanumeric characters and underscore
        String regex = "^[a-zA-Z0-9_]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        // Validate password (e.g., minimum length)
        return password.length() >= 6; // Minimum 6 characters
    }

    private boolean isValidEmail(String email) {
        // Simple email format validation using regex
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String username = usernameFieldReg.getText();
            String password = new String(passwordFieldReg.getPassword());
            String email = emailFieldReg.getText();

            if (!isValidUsername(username)) {
                JOptionPane.showMessageDialog(this, "Invalid username format!");
                return;
            }

            if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(this, "Invalid password format!");
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format!");
                return;
            }

            boolean registered = userModel.registerUser(username, password, email);

            if (registered) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!");
            }
        }

        if (e.getSource() == loginButton) {
            String username = usernameFieldLogin.getText();
            String password = new String(passwordFieldLogin.getPassword());

            boolean loggedIn = userModel.loginUser(username, password);

            if (loggedIn) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                // Perform actions after successful login
                openHomePage();

                
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        }
    }

    // Handle checkbox state change to show/hide password
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == showPasswordCheckBox) {
            int state = e.getStateChange();
            if (state == ItemEvent.SELECTED) {
                // Show password
                passwordFieldLogin.setEchoChar((char) 0);
                passwordFieldReg.setEchoChar((char) 0);
            } else {
                // Hide password
                passwordFieldLogin.setEchoChar('*');
                passwordFieldReg.setEchoChar('*');
            }
        }
    }
    private void openHomePage() {
        SwingUtilities.invokeLater(HomePageGUI::new);
        dispose(); // Close the login window after successful login
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginGUI::new);
      
    }
}