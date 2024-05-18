import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductDisplay extends JPanel {
    private JPanel contentPanel;
    DatabaseModel dbHandler = new DatabaseModel();

    public ProductDisplay() {
        setLayout(new BorderLayout());

        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(20, 3, 20, 20));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);
        List<Product> products = dbHandler.getAllSellingProducts();// fetch products from somewhere, e.g., a database

        createProductDisplay(products);
        displayProducts();
    }

    private void displayProducts() {
        // Fetch product information from the database
        List<Product> products = dbHandler.getAllSellingProducts(); // Fetch all available products in the selling state

        if (!products.isEmpty()) {
            for (Product product : products) {
                JButton productButton = createProductButton(product);
                contentPanel.add(productButton);
            }
        }
    }

    private JButton createProductButton(Product product) {
        // Create a JButton with product details
        JButton productButton = new JButton(product.getName());
        // Set other details like image, description, etc., to the button

        productButton.addActionListener(e -> {
            // Perform action when the product button is clicked
            JOptionPane.showMessageDialog(null, product.getName() + " clicked!");
        });

        return productButton;
    }
    
    private void createProductDisplay(List<Product> products) {
        JPanel contentPanel = new JPanel(new GridLayout(0, 3, 20, 20));

        for (Product product : products) {
            JPanel productPanel = new JPanel(new BorderLayout());
            productPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            productPanel.setBackground(Color.WHITE);

            JLabel imageLabel = new JLabel();
            String imagePath = product.getImagePath();

            // Set the image icon to the JLabel
            if (imagePath != null && !imagePath.isEmpty()) {
                ImageIcon icon = new ImageIcon(imagePath);
                Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Adjust dimensions as needed
                icon = new ImageIcon(image);
                imageLabel.setIcon(icon);
            }

            JLabel titleLabel = new JLabel(product.getName());
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

            productPanel.add(imageLabel, BorderLayout.CENTER); // Image in the center
            productPanel.add(titleLabel, BorderLayout.SOUTH); // Title at the bottom

            contentPanel.add(productPanel);
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       // scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Set the scroll speed

        add(scrollPane, BorderLayout.CENTER);
    }

}
