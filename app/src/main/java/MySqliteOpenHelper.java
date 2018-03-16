import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySqliteOpenHelper extends SQLiteOpenHelper {

    private static final String database_name = "databaseCeliac.db";
    private static final int database_version = 1;

    private String table_name = "USERS";

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




    public MySqliteOpenHelper(Context context){
        super(context,database_name,null,database_version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
