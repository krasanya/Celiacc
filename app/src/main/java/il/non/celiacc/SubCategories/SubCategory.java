package il.non.celiacc.SubCategories;

import java.sql.Blob;

public class SubCategory {
    private String SubCategoryName; // PRIMARY KEY   NOT NULL
    private String category; // References CATEGORIES (CategoryName) NOT NULL
    private  Blob Img;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Blob getImg() {
        return Img;
    }

    public void setImg(Blob img) {
        Img = img;
    }



    public String getSubCategoryName() {
        return SubCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.SubCategoryName = subCategoryName;
    }

    public SubCategory(){

    }

}
