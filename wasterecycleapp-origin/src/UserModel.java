public class UserModel {
    private DatabaseModel database;
    private static int loggedInUserId; // This stores the logged-in user's ID

    public UserModel() {
        this.database = new DatabaseModel();
    }
    // Setter to update the logged-in user ID during login
    public static void setLoggedInUserId(int userId) {
        loggedInUserId = userId;
    }

    // Getter to retrieve the logged-in user ID
    public  int LoggedInUserId(String username,String password) {
    	
        return database.loggedInUserId( username, password);
    }
    public boolean loginUser(String username, String password) {
        return database.verifyLogin(username, password);
    }
    public boolean registerUser(String username, String password, String email) {
        return database.registerUser(username, password, email);
    }
    public boolean buyProduct(int productId, int userId) {
        return database.buyProduct(productId, userId);
    }

    public boolean sellProduct(Product product, int userId) {
        return database.sellProduct(product, userId);
    }
    public void closeDatabaseConnection() {
        database.closeConnection();
    }
}
