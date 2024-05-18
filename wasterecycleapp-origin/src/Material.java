 import java.util.List;
public class Material {
    private String pictureLink;
    private String materialTitle;
    private String url;
    private DatabaseModel database;

    public Material(String pictureLink, String materialTitle, String url) {
        this.setPictureLink(pictureLink);
        this.materialTitle = materialTitle;
        this.url = url;
        this.database = new DatabaseModel();

    }

    public Material() {
		// TODO Auto-generated constructor stub
	}

	// Getters and setters for the fields
    // ...
    // Getter for materialTitle
    public String getMaterialTitle() {
        return materialTitle;
    }

    // Setter for materialTitle
    public void setMaterialTitle(String materialTitle) {
        this.materialTitle = "HELLO";
    }

    // Getter for URL
    public String getUrl() {
        return url;
    }

    // Setter for URL
    public void setUrl(String url) {
        this.url = url;
    }

	public String getPictureLink() {
		return pictureLink;
	}

	public void setPictureLink(String pictureLink) {
		this.pictureLink = pictureLink;
	}
	 public   List<Material> getMaterials() {
	        List<Material> materials = database.getMaterials();
	        return materials;

	 }
}