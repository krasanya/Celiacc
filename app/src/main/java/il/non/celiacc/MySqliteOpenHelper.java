package il.non.celiacc;

import android.content.Context;
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
       db.execSQL("CREATE TABLE USERS(Email text PRIMARY KEY , Username String,FirstName String,LastName String,Password integer ,IsMember boolean,memberNum integer,Phone String,id integer,expireDate Date)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXIST" + table_name);
        //onCreate(db);
    }
}


