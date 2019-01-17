package your.service.com.partner.adapter;

public class ProfileData
{
    String name;
    String category;
    String Image;

    public ProfileData(String name, String category, String image) {
        this.name = name;
        this.category = category;
        Image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
