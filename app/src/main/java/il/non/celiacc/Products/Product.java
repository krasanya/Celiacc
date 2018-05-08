package il.non.celiacc.Products;

import java.sql.Blob;
import java.util.Date;

public class Product {

    // PRODUCTS(Barcode String PRIMARY KEY NOT NULL , ProductName String NOT NULL ,CategoryName String References CATEGORIES (CategoryName) NOT NULL, subCategoryName String References SUBCAT (subCategoryName), Manufacturer String,Importer String,AdditionalInfo String ,IsGlutenFree String NOT NULL,dateValid Date,weigth double NOT NULL,IsPassover boolean NOT NULL)
    private Long Barcode; //PRIMARY KEY NOT NULL
    private String ProductName; // NOT NULL
    private String CategoryName; // References CATEGORIES (CategoryName) NOT NULL
    private String SubCategoryName; // References SUBCAT (subCategoryName)
    private String Manufacturer;
    private String Importer;
    private String AdditionalInfo;
    private Character IsGlutenFree; // NOT NULL
    private Date dateValid;
    private double weigth; // NOT NULL
    private Blob Approval;
    private boolean IsPassover;  //NOT NULL



    public Long getBarcode() {
        return Barcode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setBarcode(Long barcode) {
        this.Barcode = barcode;
    }

    public void setProductName(String productName) {
        this.ProductName = productName;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }
}
