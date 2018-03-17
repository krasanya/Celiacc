package il.non.celiacc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class MySqliteOpenHelper extends SQLiteOpenHelper {

    private static final String database_name = "databaseCeliac.db";
    private static final int database_version = 1;

    public MySqliteOpenHelper(Context context) {
        super(context, database_name,null, database_version);

    }

   /*public MySqliteOpenHelper(Context context,String database_name, SQLiteDatabase.CursorFactory factory, int database_version) {
        super(context, database_name,factory, database_version);

    }*/
    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE USERS(Email String PRIMARY KEY , Username String,FirstName String,LastName String,Password String ,IsMember boolean,memberNum integer,Phone String,id integer,expireDate Date)");

    }

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


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXIST" + table_name);
        //onCreate(db);
    }
}


