package il.non.celiacc;


public class Category {
    private String catName;
    private byte[] image;

    public Category (String catName, byte[] image){
        this.catName=catName;
        this.image=image;
    }


    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
