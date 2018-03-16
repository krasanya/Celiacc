package il.non.celiacc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySqliteOpenHelper extends SQLiteOpenHelper {

    private static final String database_name = "databaseCeliac.db";
    private static final int database_version = 1;

    private String table_users = "il.non.celiacc.USERS";


    private  String column_Email = "Email",
         column_Username = "Username",
         column_FirstName = "FirstName",
         column_LastName = "LastName",
         column_Password = "Password",
         column_IsMember = "IsMember",
         column_memberNum = "memberNum",
         column_Phone = "Phone",
         column_id = "id",
         column_expireDate = "expireDate";


    public MySqliteOpenHelper(Context context) {
            super(context, database_name, null, database_version);
            SQLiteDatabase db = this.getWritableDatabase();
        }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE" + table_users + "(Email String PRIMARY KEY AUTOINCREMENT, Username String,FirstName String,LastName String,Password integer ,IsMember boolean,memberNum integer,Phone string,id integer,expireDate date)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXIST" + table_name);
        onCreate(db);
    }
}


