package il.non.celiacc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class MySqliteOpenHelper extends SQLiteOpenHelper {

    private static final String database_name = "databaseCeliac.db";
    private static final int database_version = 8;

    public MySqliteOpenHelper(Context context) {
        super(context, database_name,null, database_version);
    }
    ArrayList<HashMap<String,String>> products = new  ArrayList<HashMap<String,String>>();
    ArrayList<String> AllProducts = new  ArrayList<String>();
    ArrayList<HashMap<String,String>> AllCategories = new  ArrayList<HashMap<String,String>>();
    //HashMap<String,String> AllCategories = new  HashMap<String,String>();
    ArrayList<HashMap<String,String>> AllSubCategories = new  ArrayList<HashMap<String,String>>();
    ArrayList<String> AllManu = new  ArrayList<String>();
    final SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy");
    ///////////////

    @Override
    public void onCreate(SQLiteDatabase db) {
     //   onUpgrade(db,7,8);
       db.execSQL("CREATE TABLE IF NOT EXISTS USERS(Email String PRIMARY KEY NOT NULL, Username String NOT NULL,FirstName String NOT NULL,LastName String NOT NULL,Password String NOT NULL,IsMember boolean,memberNum integer,Phone String,id integer,expireDate Date)");
        db.execSQL("CREATE TABLE IF NOT EXISTS CATEGORIES(CategoryName String PRIMARY KEY NOT NULL , Tag1 String,Tag2 String,Tag3 String,Tag4 String,Tag5 String,Tag6 String,Tag7 String,Tag8 String,Tag9 String,Tag10 String,Img BLOB)");
       db.execSQL("CREATE TABLE IF NOT EXISTS SUBCAT(subCategoryName  PRIMARY KEY   NOT NULL , CategoryName String References CATEGORIES (CategoryName) NOT NULL,Img BLOB) ");
       db.execSQL("CREATE TABLE IF NOT EXISTS PRODUCTS(Barcode String PRIMARY KEY NOT NULL , ProductName String NOT NULL ,CategoryName String References CATEGORIES (CategoryName) NOT NULL, subCategoryName String References SUBCAT (subCategoryName), Manufacturer String,Importer String,AdditionalInfo String ,IsGlutenFree String NOT NULL,dateValid Date,weigth double NOT NULL,IsPassover boolean NOT NULL)");

    }

    /* HELP INSERT PRODUCTS TO TABLE PRODUCTS */
     public boolean addProduct(String Barcode, String ProductName  , String CategoryName  ,String subCategoryName,String Manufacturer , String Importer , String AdditionalInfo  , String IsGlutenFree ,/* blob IMG,*/Date dateValid , double weigth ,/*blob Approval ,*/boolean IsPassover  ){

         SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Barcode", Barcode);
        cv.put("ProductName",ProductName);
        cv.put("CategoryName",CategoryName);
         cv.put("subCategoryName",subCategoryName);
         cv.put("Manufacturer", Manufacturer);
        cv.put("Importer",Importer);
        cv.put("AdditionalInfo", AdditionalInfo);
        cv.put("IsGlutenFree",IsGlutenFree);
        cv.put("dateValid", parser.format(dateValid));
        cv.put("weigth",weigth);
        cv.put("IsPassover",IsPassover);
        db.insert("PRODUCTS",null,cv);
        db.close();
        return true;
    }
    /* HELP INSERT CATEGORIES TO TABLE CATEGORIES */
    public boolean addCategory(String CategoryName , String Tag1 ,String Tag2 ,String Tag3 ,String Tag4,String Tag5,String Tag6,String Tag7,String Tag8,String Tag9,String Tag10,byte[] img ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("CategoryName",CategoryName);
        cv.put("Tag1",Tag1);
        cv.put("Tag2",Tag2);
        cv.put("Tag3",Tag3);
        cv.put("Tag4",Tag4);
        cv.put("Tag5",Tag5);
        cv.put("Tag6",Tag6);
        cv.put("Tag7",Tag7);
        cv.put("Tag8",Tag8);
        cv.put("Tag9",Tag9);
        cv.put("Tag10",Tag10);
        //cv.put("img",img);
        db.insert("CATEGORIES",null,cv);
        db.close();
        return true;
    }

    /* HELP INSERT SUBCATEGORIES TO TABLE SUBCATEGORIES */
    public boolean addSubCategory(String subCategoryName , String CategoryName,byte[] img  ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("subCategoryName",subCategoryName);
        cv.put("CategoryName",CategoryName);
      //  cv.put("img",img);
        db.insert("SUBCAT",null,cv);
        db.close();
        return true;
    }

    /* INSERT USER TO TABLE USERS */
    public boolean addUser(String Email , String Username ,String FirstName ,String LastName ,String Password ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Email",Email);
        cv.put("Username",Username);
        cv.put("Firstname",FirstName);
        cv.put("Lastname",LastName);
        cv.put("Password", Password);
        db.insert("USERS",null,cv);
        db.close();
        return true;
    }

    /* GET EMAIL BY USERNAME */
 public String getEmailByUsername(String Username){
     SQLiteDatabase db = getReadableDatabase();
     Cursor c = db.rawQuery("SELECT Email FROM USERS WHERE Username = '"+Username+"'",null);
     if (c.getCount()==0) {return "דואר אלקטרוני";}
     c.moveToFirst();
     String answer = c.getString(0);
     c.close();
     return answer;
 }
    /* GET USERNAME BY USERNAME */
    public String getUsernameFromUsername(String Username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Username FROM USERS WHERE Username = '"+Username+"'",null);
        if (c.getCount()==0) {return "שם משתמש";}
        c.moveToFirst();
        String answer = c.getString(0);
        c.close();
        return answer;
    }
    /* GET FIRST NAME BY USERNAME */
    public String getFirstNameFromUsername(String Username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Firstname FROM USERS WHERE Username = '"+Username+"'",null);
        if (c.getCount()==0) {return "שם פרטי";}
        c.moveToFirst();
        String answer = c.getString(0);
        c.close();
        return answer;
    }
    /* GET LAST NAME BY USERNAME */
    public String getLastnameFromUsername(String Username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Lastname FROM USERS WHERE Username = '"+Username+"'",null);
        if (c.getCount()==0) {return "שם משתמש";}
        c.moveToFirst();
        String answer = c.getString(0);
        c.close();
        return answer;
    }
    /* GET LAST NAME BY USERNAME */
    public String getPassFromUsername(String Username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Password FROM USERS WHERE Username = '"+Username+"'",null);
        if (c.getCount()==0) {return "סיסמא";}
        c.moveToFirst();
        String answer = c.getString(0);
        c.close();
        return answer;
    }

    /* IS THERE USERNAME IN THE DATABASE WITH THE SAME USERNAME */
    public boolean IsUsername(String Username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM USERS WHERE Username = '"+Username+"'",null);
        if (c.getCount()==0) {return false;}
        c.close();
        return true;
    }
    /* IS THERE USERNAME IN THE DATABASE WITH THE SAME EMAIL */
    public boolean IsEmail(String Email){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM USERS WHERE Email = '"+Email+"'",null);
        if (c.getCount()==0) {return false;}
        c.close();
        return true;
    }
    /* IS THIS THE PASSWORD OF THIS USERNAME */
    public boolean confirmUserPassword(String Username,String password){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM USERS WHERE Username = '"+Username+"' AND Password = '"+password+"' " ,null);
        if (c.getCount()==0) {return false;}
        c.close();
        return true;
    }

    /* UPDATE USER */
    public boolean UpdateUser(String OldEmail, String Email , String Username ,String FirstName ,String LastName ,String Password ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Email",Email);
        cv.put("Username",Username);
        cv.put("Firstname",FirstName);
        cv.put("Lastname",LastName);
        cv.put("Password", Password);
        db.update("USERS",cv,"Email = ?" ,new String[] {OldEmail});
        db.close();
        return true;
    }

    /* GET ALL PRODUCTS NAME */
    public ArrayList<String> getProductName() {
        Cursor c = getReadableDatabase().rawQuery("SELECT DISTINCT ProductName FROM PRODUCTS ORDER BY ProductName ASC", null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                AllProducts.add(c.getString(0));
                c.moveToNext();
            }
            c.close();
        }
        return AllProducts;
    }
    /* GET ALL MANUFACTURERS AND IMPORTERS NAME */
    public ArrayList<String> getManuName() {
        Cursor c = getReadableDatabase().rawQuery("SELECT DISTINCT Manufacturer FROM PRODUCTS ORDER BY Manufacturer ASC", null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                Log.d("ALL MANU ",c.getString(0));
                AllManu.add(c.getString(0));
                c.moveToNext();
            }
        }
//            c = getReadableDatabase().rawQuery("SELECT DISTINCT Importer FROM PRODUCTS ORDER BY Importer ASC", null);
//            if (c.getCount() > 0) {
//                c.moveToFirst();
//                for (int i = 0; i < c.getCount(); i++) {
//                    AllManu.add(c.getString(0));
//                     c.moveToNext();
//                }
//            c.close();
//        }
        return AllManu;
    }

    /* GET ALL CATEGORIES NAME */
    public ArrayList<HashMap<String,String>> getAllCategories() {
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM CATEGORIES ORDER BY CategoryName DESC ", null);
        //byte[] bytes = c.getBlob(11);
        if (c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                //hm.put("CategoryName", c.getBlob(11));
                //Log.d("BLOBBB",c.isNull());
              //  hm.put("Img", c.getBlob(11));
                hm.put("CategoryName", c.getString(0));
                hm.put("Tag1", c.getString(1));
                AllCategories.add(hm);
                c.moveToNext();
            }
            c.close();
        }
        return AllCategories;
    }
    /* GET ALL SUBCATEGORIES NAME */
    public ArrayList<HashMap<String,String>> getSubCategories(String cat) {
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM SUBCAT WHERE CategoryName = '"+cat+"' ORDER BY subCategoryName DESC ", null);
        //byte[] bytes = c.getBlob(11);
        if (c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("subCategoryName", c.getString(0));
                hm.put("CategoryName", c.getString(1));
                AllSubCategories.add(hm);
                c.moveToNext();
            }
            c.close();
        }
        return AllSubCategories;
    }

//    public HashMap<String,String> getAllCategories() {
//        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM CATEGORIES ORDER BY CategoryName DESC ", null);
//        //byte[] bytes = c.getBlob(11);
//        if (c.getCount() > 0) {
//            c.moveToFirst();
//            for (int i = 0; i < c.getCount(); i++) {
//                HashMap<String, String> hm = new HashMap<String, String>();
//                AllCategories.put("CategoryName", c.getString(0));
//                AllCategories.put("Tag1", c.getString(1));
//                //AllCategories.add(hm);
//                c.moveToNext();
//            }
//            c.close();
//        }
//        return AllCategories;
//    }
    /* GET ALL PRODUCTS FROM SPECIFIC SUBCATEGORY */
     public ArrayList<HashMap<String,String>> getSubProductList(String subCat) {
        Cursor c = null;
        if (!subCat.equals("")) {
              c = getReadableDatabase().rawQuery("SELECT * FROM PRODUCTS WHERE subCategoryName ='" + subCat + "' ORDER BY ProductName DESC ", null);
            }

        if (c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("Product", c.getString(1));
                hm.put("Barcode", c.getString(0));
                products.add(hm);
                c.moveToNext();
            }
        }
        c.close();
        return products;
    }
    /* GET ALL PRODUCTS THAT ARE LIKE "Product name" FROM SPESIFIC MANUFACTURER */
    public ArrayList<HashMap<String,String>> getProducts(String prodName, String manu) {
       Cursor c = null;
       if (!prodName.equals("")) {
           if (!manu.equals("")) {
                   c = getReadableDatabase().rawQuery("SELECT * FROM PRODUCTS WHERE (TRIM(ProductName) LIKE '%" + prodName + "%') AND (TRIM(Manufacturer) LIKE '%" + manu + "%' OR TRIM(Importer) LIKE '%" + manu + "%' ORDER BY ProductName DESC", null);
           }
           else {
                   c = getReadableDatabase().rawQuery("SELECT * FROM PRODUCTS WHERE TRIM(ProductName) LIKE '%" + prodName + "%' ORDER BY ProductName DESC", null);
               }
       }
       else {
             if (!manu.equals("")) {
                   c = getReadableDatabase().rawQuery("SELECT * FROM PRODUCTS WHERE (TRIM(Manufacturer) LIKE '%" + manu + "%' OR TRIM(Importer) LIKE '%" + manu + "%')ORDER BY ProductName DESC ", null);
             }
           }
        if (prodName.equals("") & manu.equals("")){
             c = getReadableDatabase().rawQuery("SELECT * FROM PRODUCTS ORDER BY ProductName DESC ", null);
           }

        if (c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
               hm.put("Product", c.getString(1));
               hm.put("Barcode", c.getString(0));
                products.add(hm);
                c.moveToNext();
            }
        }
        c.close();
       return products;
    }
    /* GET PRODUCT FROM SPECIFIC BARCODE */
      public Cursor findProductBarcodeCursor(String BarcodeScan) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM PRODUCTS WHERE Barcode = '"+BarcodeScan+"'",null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            return c;
        }
        else return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("DROP TABLE IF EXISTS USERS");
        db.execSQL("DROP TABLE IF EXISTS PRODUCTS");
        db.execSQL("DROP TABLE IF EXISTS SUBCAT");
        db.execSQL("DROP TABLE IF EXISTS CATEGORIES");
       // db.execSQL("CREATE TABLE IF NOT EXISTS USERS(Email String PRIMARY KEY NOT NULL, Username String NOT NULL,FirstName String NOT NULL,LastName String NOT NULL,Password String NOT NULL,IsMember boolean,memberNum integer,Phone String,id integer,expireDate Date)");
        db.execSQL("CREATE TABLE IF NOT EXISTS CATEGORIES(CategoryName String PRIMARY KEY NOT NULL , Tag1 String,Tag2 String,Tag3 String,Tag4 String,Tag5 String,Tag6 String,Tag7 String,Tag8 String,Tag9 String,Tag10 String,Img BLOB)");
        db.execSQL("CREATE TABLE IF NOT EXISTS SUBCAT(subCategoryName  PRIMARY KEY   NOT NULL , CategoryName String References CATEGORIES (CategoryName) NOT NULL,Img BLOB) ");
        db.execSQL("CREATE TABLE IF NOT EXISTS PRODUCTS(Barcode String PRIMARY KEY NOT NULL , ProductName String NOT NULL ,CategoryName String References CATEGORIES (CategoryName) NOT NULL, subCategoryName String References SUBCAT (subCategoryName), Manufacturer String,Importer String,AdditionalInfo String ,IsGlutenFree String NOT NULL,dateValid Date,weigth double NOT NULL,IsPassover boolean NOT NULL)");

        //onCreate(db);
    }
}



