package il.non.celiacc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class MySqliteOpenHelper extends SQLiteOpenHelper {

    private static final String database_name = "databaseCeliac.db";
    private static final int database_version = 11;

    public MySqliteOpenHelper(Context context) {
        super(context, database_name,null, database_version);


    }
    final SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy");
    ///////////////

    @Override
    public void onCreate(SQLiteDatabase db) {
        //onUpgrade(db,10,11);
       db.execSQL("CREATE TABLE IF NOT EXISTS USERS(Email String PRIMARY KEY NOT NULL, Username String NOT NULL,FirstName String NOT NULL,LastName String NOT NULL,Password String NOT NULL,IsMember boolean,memberNum integer,Phone String,id integer,expireDate Date)");
      db.execSQL("CREATE TABLE IF NOT EXISTS CATEGORIES(CategoryName String PRIMARY KEY NOT NULL , Tag1 String,Tag2 String,Tag3 String,Tag4 String,Tag5 String,Tag6 String,Tag7 String,Tag8 String,Tag9 String,Tag10 String)");
      db.execSQL("CREATE TABLE IF NOT EXISTS SUBCAT(subCategoryName String PRIMARY KEY NOT NULL, CategoryName CONSTRAINT category_pk REFERENCES CATEGORIES NOT NULL)");
      db.execSQL("CREATE TABLE IF NOT EXISTS PRODUCTS(Barcode String PRIMARY KEY NOT NULL , ProductName String NOT NULL ,CategoryName CONSTRAINT category_pk REFERENCES CATEGORIES NOT NULL, subCategoryName CONSTRAINT subCategory_pk REFERENCES SUBCAT NOT NULL, Manufacturer String,Importer String,AdditionalInfo String ,IsGlutenFree String NOT NULL,/*IMG blob ,*/dateValid Date,weigth double NOT NULL,/*Approval blob,*/IsPassover boolean NOT NULL)");



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
    public boolean addCategory(String CategoryName , String Tag1 ,String Tag2 ,String Tag3 ,String Tag4,String Tag5,String Tag6,String Tag7,String Tag8,String Tag9,String Tag10 ){
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
        db.insert("CATEGORIES",null,cv);
        db.close();
        return true;
    }

    /* HELP INSERT SUBCATEGORIES TO TABLE SUBCATEGORIES */
    public boolean addSubCategory(String subCategoryName, String CategoryName  ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("subCategoryName",subCategoryName);
        cv.put("CategoryName",CategoryName);

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

    public boolean selectUserByUsername(String Username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM USERS WHERE TRIM(Username) = '"+Username+"'",null);
        if (c.getCount()==0) {return false;}
        c.close();
        return true;
    }

    public boolean selectUserByEmail(String Email){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM USERS WHERE TRIM(Email) = '"+Email+"'",null);
        if (c.getCount()==0) {return false;}
        c.close();
        return true;
    }


    public boolean confirmUserPassword(String Username,String password){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM USERS WHERE TRIM(Username) = '"+Username+"' AND TRIM(Password) = '"+password+"' " ,null);
        if (c.getCount()==0) {return false;}
        c.close();
        return true;
    }

    public String findProductBarcode(String BarcodeScan) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM PRODUCTS WHERE TRIM(Barcode) = '"+BarcodeScan+"'",null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            {
                if (c.getString(c.getColumnIndex("IsGlutenFree")).equals("Y")) {
                    return "Y";
                }
                if (c.getString(c.getColumnIndex("IsGlutenFree")).equals("N")) {
                    return "N";
                }
                if (c.getString(c.getColumnIndex("IsGlutenFree")).equals("M")) {
                    return "M";
                } else {
                    c.close();
                    return "Not Found";
                }
            }


        }
        else return "Didn't Find";
    }

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
        db.execSQL("DROP TABLE IF EXISTS USERS");
        db.execSQL("DROP TABLE IF EXISTS PRODUCTS");
        db.execSQL("DROP TABLE IF EXISTS SUBCAT");
        db.execSQL("DROP TABLE IF EXISTS CATEGORIES");
        db.execSQL("CREATE TABLE  USERS(Email String PRIMARY KEY NOT NULL, Username String NOT NULL,FirstName String NOT NULL,LastName String NOT NULL,Password String NOT NULL,IsMember boolean,memberNum integer,Phone String,id integer,expireDate Date)");
        db.execSQL("CREATE TABLE  CATEGORIES(CategoryName String PRIMARY KEY NOT NULL , Tag1 String,Tag2 String,Tag3 String,Tag4 String,Tag5 String,Tag6 String,Tag7 String,Tag8 String,Tag9 String,Tag10 String)");
        db.execSQL("CREATE TABLE  SUBCAT(subCategoryName String PRIMARY KEY NOT NULL, CategoryName CONSTRAINT category_pk REFERENCES CATEGORIES NOT NULL)");
         db.execSQL("CREATE TABLE  PRODUCTS(Barcode String PRIMARY KEY NOT NULL , ProductName String NOT NULL ,CategoryName CONSTRAINT category_pk REFERENCES CATEGORIES NOT NULL, subCategoryName CONSTRAINT subCategory_pk REFERENCES SUBCAT NOT NULL, Manufacturer String,Importer String,AdditionalInfo String ,IsGlutenFree String NOT NULL,/*IMG blob ,*/dateValid Date,weigth double NOT NULL,/*Approval blob,*/IsPassover boolean NOT NULL)");


        //onCreate(db);
    }


}



