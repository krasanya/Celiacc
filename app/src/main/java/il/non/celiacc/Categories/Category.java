package il.non.celiacc.Categories;

import java.sql.Blob;

public class Category {

   private String CategoryName;  //PRIMARY KEY NOT NULL
    private String IMG;
 private String Tag1 ;
 private String Tag2 ;
 private String Tag3 ;
 private  String Tag4 ;
 private String Tag5 ;
 private String Tag6 ;
 private String Tag7 ;
 private String Tag8 ;
 private String Tag9 ;
 private String Tag10 ;

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }



    public Category(){

    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public void setTag1(String tag1) {
        Tag1 = tag1;
    }

 public String getTag1() {
  return Tag1;
 }

}
