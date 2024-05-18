import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

public class LearnScreen extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color lightGreen = new Color(34, 139, 34);
    private List<Material> materials;

    DatabaseModel dbHandler = new DatabaseModel();

    public LearnScreen() {
        setTitle("Learn Materials");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        setLayout(new BorderLayout());
        getContentPane().setBackground(lightGreen);

        // Create close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        // Add close button to top-right corner
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.add(closeButton);
        add(topRightPanel, BorderLayout.NORTH);

        createLearningMaterials(); // Call the method to create learning materials

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createLearningMaterials() {
        JPanel materialsPanel = new JPanel(new GridLayout(0, 1)); // One column layout for materials
        materialsPanel.setBackground(Color.green);

        materials = dbHandler.getMaterials();
        System.out.println("Materials retrieved: " + materials.size());

        for (Material material : materials) {
            JPanel materialPanel = new JPanel(new BorderLayout());
            materialPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            materialPanel.setBackground(Color.WHITE);

            JLabel titleLabel = new JLabel(material.getMaterialTitle());
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
           

            final String url = material.getUrl();
            JLabel urlLabel = new JLabel("<html><a href='" + url + "'>" + "Click to Visit" + "</a></html>");
            urlLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            urlLabel.setForeground(Color.BLUE.darker());
            urlLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            urlLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().browse(new URI(url));
                        } catch (IOException | URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            
         // Assuming material.getPictureLink() returns the path to the image file
            String imagePath = material.getPictureLink();

            // Create a JLabel to hold the image
            JLabel imageLabel = new JLabel();

            // Set the image icon to the JLabel
            if (imagePath != null && !imagePath.isEmpty()) {
                ImageIcon icon = new ImageIcon(imagePath);
                Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Set image size
                icon = new ImageIcon(image);
                imageLabel.setIcon(icon);
            }

            // Add the image label to the panel
            materialPanel.add(imageLabel, BorderLayout.WEST); // Assuming 'materialPanel' is your JPanel


            materialPanel.add(titleLabel, BorderLayout.NORTH);
              materialPanel.add(urlLabel, BorderLayout.CENTER);

            materialsPanel.add(materialPanel);
        }

        JScrollPane scrollPane = new JScrollPane(materialsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Set the scroll speed

        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LearnScreen::new);
    }
}
