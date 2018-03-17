package il.non.celiacc;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

     private MySqliteOpenHelper myHelper;
     private SQLiteDatabase sqlDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //db
        //myHelper = new MySqliteOpenHelper(MainActivity.this, "CeliacDB",null,1);
        //sqlDB = myHelper.getWritableDatabase();

       // final EditText etUsername = (EditText) findViewById(R.id.etUsername);
       // final EditText etPassword = (EditText) findViewById(R.id.etPassword);


        Button Menubutton = (Button) findViewById(R.id.btLogin);
        Menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MenuIntent = new Intent (MainActivity.this,MainMenu.class);
                startActivity(MenuIntent);
            }
        });

        Button Registerbutton = (Button) findViewById(R.id.btRegister);
        Registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent NewUserIntent = new Intent (MainActivity.this,NewUserForm.class);
                startActivity(NewUserIntent);
            }
        });


    }
}
