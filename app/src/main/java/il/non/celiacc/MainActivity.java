package il.non.celiacc;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    MySqliteOpenHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MySqliteOpenHelper(this);

        Button Menubutton = (Button) findViewById(R.id.btLogin);
        Menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final EditText etUsername = (EditText) findViewById(R.id.etUsername);
                    final EditText etPassword = (EditText) findViewById(R.id.etPassword);

                    String strUsername = etUsername.getText().toString();
                    String strPass = etPassword.getText().toString();

                 if (v.getId() == R.id.btLogin) {

                     if (db.selectUserByUsername(strUsername) == true){
                         Toast.makeText(getApplicationContext(),"USER EXISTS",Toast.LENGTH_LONG)
                                 .show();
                     }
                     else {
                         Toast.makeText(getApplicationContext(),"NOT EXIST",Toast.LENGTH_LONG)
                                 .show();
                     }

                    Intent MenuIntent = new Intent(MainActivity.this, MainMenu.class);
                    startActivity(MenuIntent);
                 }

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
