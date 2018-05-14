package il.non.celiacc.Products;

public class Product {

    // PRODUCTS(Barcode String PRIMARY KEY NOT NULL , ProductName String NOT NULL ,CategoryName String References CATEGORIES (CategoryName) NOT NULL, subCategoryName String References SUBCAT (subCategoryName), Manufacturer String,Importer String,AdditionalInfo String ,IsGlutenFree String NOT NULL,dateValid Date,weight double NOT NULL,IsPassover boolean NOT NULL)
    private Long Barcode; //PRIMARY KEY NOT NULL
    private String ProductName; // NOT NULL
    private String CategoryName; // References CATEGORIES (CategoryName) NOT NULL
    private String SubCategoryName; // References SUBCAT (subCategoryName)
    private String Manufacturer;
    private String Importer;
    private String AdditionalInfo;
    private String IsGlutenFree; // NOT NULL
    private String DateValid;
    private int weight; // NOT NULL
    //private Blob Approval;
    private boolean IsPassover;  //NOT NULL

    public Long getBarcode() {

        return this.Barcode;
    }

    public String getProductName() {

        return this.ProductName;
    }

    public void setBarcode(Long barcode) {

        this.Barcode = barcode;
    }

    public void setProductName(String productName) {

        this.ProductName = productName;
    }

    public String getCategoryName() {

        return this.CategoryName;
    }

    public void setCategoryName(String categoryName) {

        CategoryName = categoryName;
    }

    public String getManufacturer() {

        return this.Manufacturer;
    }

    public void setManufacturer(String manufacturer)
    {
        Manufacturer = manufacturer;
    }

    public String getSubCategoryName() {
        return SubCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        SubCategoryName = subCategoryName;
    }

    public String getImporter() {
        return this.Importer;
    }

    public void setImporter(String importer) {
        Importer = importer;
    }

    public String getAdditionalInfo() {
        return this.AdditionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        AdditionalInfo = additionalInfo;
    }


    public String getDateValid() {
        return this.DateValid;
    }

    public void setDateValid(String dateValid) {
//        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        this.DateValid = dateFormat.format(dateValid).toString();
        this.DateValid = dateValid;
    }

    public String getIsGlutenFree() {
        return this.IsGlutenFree;
    }

    public void setIsGlutenFree(String isGlutenFree) {
        this.IsGlutenFree = isGlutenFree;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

//    public Blob getApproval() {
//        return Approval;
//    }
//
//    public void setApproval(Blob approval) {
//        Approval = approval;
//    }
//
    public boolean isPassover() {
        return IsPassover;
    }

    public void setPassover(boolean passover) {
        IsPassover = passover;
    }


}
