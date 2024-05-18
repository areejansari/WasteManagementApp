import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ProductGui extends JFrame implements ActionListener {
    private JButton addButton;
    private JButton viewButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JPanel productPanel;

    public ProductGui() {
        setTitle("Product CRUD Operations");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setUndecorated(true);      
        JPanel closebtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton closeButton = new JButton("Close");
         closebtn.add(closeButton);
          closeButton.addActionListener(e -> dispose());
         

        JPanel bottomNavBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomNavBar.setBackground(new Color(240, 240, 240));

        addButton = createStyledButton("Add Product");
        viewButton = createStyledButton("View Products");
        updateButton = createStyledButton("Update Product");
        deleteButton = createStyledButton("Delete Product");

        bottomNavBar.add(addButton);
        bottomNavBar.add(viewButton);
       // bottomNavBar.add(updateButton);
        bottomNavBar.add(deleteButton);

        add(bottomNavBar, BorderLayout.SOUTH);
        add(closebtn,BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(140, 30));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(60, 179, 113));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            openAddProductScreen();
        } else if (e.getSource() == viewButton) {
            openViewProductsScreen();
        } else if (e.getSource() == updateButton) {
            openUpdateProductScreen();
        } else if (e.getSource() == deleteButton) {
            openDeleteProductScreen();
        }  
    }

    private void openAddProductScreen() {
        String productName = JOptionPane.showInputDialog("Enter product name:");
        String priceStr = JOptionPane.showInputDialog("Enter product price:");
        String desc = JOptionPane.showInputDialog("Enter product desc:");
        String imagePath = null;
        try {
            double price = Double.parseDouble(priceStr);
            Product product = new Product(desc, productName, price, imagePath);
            product.setImagePath();

            System.out.print(product);
            boolean added = product.addProduct();
            if (added) {
                JOptionPane.showMessageDialog(this, "Product added successfully");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add product");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price format");
        }
    }
    private void openViewProductsScreen() {
    	System.out.println("Hei");
    	String userIdString = JOptionPane.showInputDialog("Enter your user ID:");

         try {
            int userId = Integer.parseInt(userIdString);
            // Now you have the user ID as an integer value
            List<Product> productList = Product.getProductsByUserId(userId);
            // Use the user ID (int) in your application logic
            if (!productList.isEmpty()) {
                // Create a 2D array to hold product information
                String[][] productData = new String[productList.size()][4]; // Four columns

                // Populate the data array with product information
                for (int i = 0; i < productList.size(); i++) {
                    Product product = productList.get(i);
                    productData[i][0] = product.getName();
                    productData[i][1] = String.valueOf(product.getPrice());
                    productData[i][2] = product.getdesc();
                    productData[i][3] = product.getImagePath();
                }

                // Define column names
                String[] columnNames = {"Product Name", "Price", "Description", "Image Path"};

                // Create a table model using the product data and column names
                DefaultTableModel model = new DefaultTableModel(productData, columnNames);

                // Create a JTable using the model
                JTable table = new JTable(model);

                // Place the table in a JScrollPane for scrolling if needed
                JScrollPane scrollPane = new JScrollPane(table);

                // Create a JFrame to display the table
                JFrame frame = new JFrame("Products");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(scrollPane, BorderLayout.CENTER);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }  
            
            else {
                JOptionPane.showMessageDialog(null, "No products found for the user");
            }
         }
            catch (NumberFormatException e) {
            // Handle the case where the input string is not a valid integer
            System.out.println("Invalid user ID format. Please enter a valid integer.");
        }
        
        
    }

    public void ProductPanel() {
        setTitle("Product List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        productPanel = new JPanel(new GridLayout(0, 1)); // Panel to display products
        JScrollPane scrollPane = new JScrollPane(productPanel);

        add(scrollPane, BorderLayout.CENTER);

        displayProducts(); // Display products on initialization
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void displayProducts() {
        List<Product> productList = Product.getProductsByUserId(Product.userId());

        if (!productList.isEmpty()) {
            for (Product product : productList) {
                JLabel productLabel = new JLabel(product.toString());
                productPanel.add(productLabel);
            }
        } else {
            JLabel noProductLabel = new JLabel("No products found for the user");
            productPanel.add(noProductLabel);
        }
    }

    private void openUpdateProductScreen() {
        displayProducts();

        String productName = JOptionPane.showInputDialog("Enter updated product name:");
        String productdesc = JOptionPane.showInputDialog("Enter updated product Description:");
        String priceStr = JOptionPane.showInputDialog("Enter updated product price:");
        String imagePath = JOptionPane.showInputDialog("Enter product imagepath:");

        try {
            double price = Double.parseDouble(priceStr);
            Product product = new Product(productName, productdesc, price, imagePath);
            boolean updated = product.updateProduct();
            if (updated) {
                JOptionPane.showMessageDialog(this, "Product updated successfully");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update product");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price format");
        }
    }

    private void openDeleteProductScreen() {
        int productId = Integer.parseInt(JOptionPane.showInputDialog("Enter product ID to delete:"));
        boolean deleted = Product.deleteProduct(productId);
        if (deleted) {
            JOptionPane.showMessageDialog(this, "Product deleted successfully");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete product");
        }
    }
    

    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(ProductGui::new);
    }
}
