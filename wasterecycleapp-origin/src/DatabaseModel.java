import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 import java.util.List;
 import java.util.ArrayList;

public class DatabaseModel {
    private Connection connection;

    public DatabaseModel() {
        try {
            // Establishing the database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wasterecycledb?serverTimezone=UTC", "root", "root");
            System.out.println("Connected to the database!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public int loggedInUserId(String username, String password) {
        int userId = -1; // Default value to indicate user not found or error
        
        try {
            String query = "SELECT id FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions (logging, error handling, etc.)
        }
        
        return userId;
    }

    public boolean verifyLogin(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); // Returns true if the user exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean registerUser(String username, String password, String email) {
        try {
            String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // Returns true if insertion successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean buyProduct(int productId, int userId) {
        try {
            // Simplified logic to update user's purchased products
            String query = "INSERT INTO purchased_products (user_id, product_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sellProduct(Product product, int userId) {
        try {
            // Simplified logic to add product to the selling list
            String query = "INSERT INTO selling_products ( product_name, price,description) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setString(2, product.getdesc());
            statement.setDouble(3, product.getPrice());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
 // Method to add a product to the database
    public boolean addProduct(Product product) {
    	String query = "INSERT INTO products (product_name, price, description, image_path) VALUES (?, ?, ?, ?)";

    	try (PreparedStatement statement = connection.prepareStatement(query)) {
    	    statement.setString(1, product.getName());
    	    statement.setDouble(2, product.getPrice());
    	    statement.setString(3, product.getdesc());
    	    statement.setString(4, product.getImagePath()); // Assuming product.getImagePath() returns the image path

    	    int rowsInserted = statement.executeUpdate();
    	    return rowsInserted > 0;
    	} catch (SQLException e) {
    	    // Handle the SQL exception
    	    e.printStackTrace();
    	    return false;
    	}

    }
    
 // Method to update a product in the database
    public boolean updateProduct(Product product) {
    	 String query = "UPDATE products SET product_name = ?, description = ?, price = 100 WHERE product_id = ?";
    	    try (PreparedStatement statement = connection.prepareStatement(query)) {
    	        statement.setString(1, product.getName());
    	        statement.setString(3, product.getdesc());
    	        statement.setDouble(2, product.getPrice());
    	       // statement.setInt(4, product.getId()); // Assuming there's a method like getId() in the Product class

    	        int rowsUpdated = statement.executeUpdate();
    	        return rowsUpdated > 0;
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	        return false;
    	    }
    }
    // Method to delete a product from the database
    public boolean deleteProduct(int productId) {
        String query = "DELETE FROM products WHERE product_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve a product by ID from the database
    public Product getProductById(int productId) {
    	String query = "SELECT * FROM products";
    	try (PreparedStatement statement = connection.prepareStatement(query)) {
    	    statement.setInt(1, productId);

    	    ResultSet resultSet = statement.executeQuery();
    	    if (resultSet.next()) {
    	        String name = resultSet.getString("product_name");
    	        String desc = resultSet.getString("description");
    	        double price = resultSet.getDouble("price");
    	        String imagePath = resultSet.getString("image_path"); // Replace 'image_path' with the actual column name

    	        // Create a new Product object with retrieved details
    	        Product product = new Product(desc, name, price, imagePath);
    	        product.setId(productId); // Set the ID of the product

    	        return product;
    	    }
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
    	return null;

    }
    
    public  void queryprocess( List<Product> productList,int user_id) {
       
 
    	
    	 String query = "SELECT p.* FROM products p " +
                 "INNER JOIN selling_products sp ON p.product_id = sp.product_id " +
                 "WHERE sp.user_id = ?";
         try (PreparedStatement statement = connection.prepareStatement(query)) {
             statement.setInt(1, user_id);

             ResultSet resultSet = statement.executeQuery();
             while (resultSet.next()) {
                 int productId = resultSet.getInt("product_id");
                 String productName = resultSet.getString("product_name");
                 double price = resultSet.getDouble("price");
                 String description = resultSet.getString("description");
                 String imagePath = resultSet.getString("image_path");

                 Product product = new Product(productName, description, price, imagePath);
                 productList.add(product);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }
    
    
    // Method to retrieve materials information from the database
    public List<Material> getMaterials() {
        List<Material> materials = new ArrayList<>();

        try {
             String query = "SELECT * FROM materials"; // Query to retrieve materials from the database
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery(); // Execute the query to retrieve data

            while (resultSet.next()) {
                String materialTitle = resultSet.getString("title");
                String pictureLink = resultSet.getString("picture_link");

                String url = resultSet.getString("url");

                // Create Material objects using retrieved data and add them to a list or collection
                Material material = new Material(pictureLink, materialTitle, url);
                materials.add(material);
            }

            resultSet.close();
            statement.close();
            // Handle exceptions, close connections, etc.
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions accordingly
        }
		return materials;

        
    }

    public List<Product> getAllSellingProducts() {
        List<Product> products = new ArrayList<>();

        try {
            String query = "SELECT * FROM products"; // Adjust the query based on your database structure WHERE status = 'selling'
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String productId = resultSet.getString("product_id");
                String productName = resultSet.getString("product_name");
                double price = resultSet.getDouble("price");
                String imagePath = resultSet.getString("image_path");
                String description = resultSet.getString("description");

                Product product = new Product(productName, description, price, imagePath);
                products.add(product);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions accordingly
        }

        return products;
    }
    private static Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
