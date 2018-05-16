package il.non.celiacc.SubCategories;

import java.sql.Blob;

public class SubCategory {
    private String SubCategoryName; // PRIMARY KEY   NOT NULL
    private String category; // References CATEGORIES (CategoryName) NOT NULL
    private  String IMG;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImg() {
        return IMG;
    }

    public void setImg(String img) {
        IMG = img;
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
