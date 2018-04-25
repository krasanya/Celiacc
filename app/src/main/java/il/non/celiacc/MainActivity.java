package il.non.celiacc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;
/////////////////


public class MainActivity extends AppCompatActivity {

    MySqliteOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
       // Toolbar toolbar= (Toolbar)findViewById(R.id.);
        getSupportActionBar().setTitle("מדריך המזון הרשמי לקהילת הצליאק");
        getSupportActionBar().setLogo(android.R.drawable.ic_menu_info_details);

        db = new MySqliteOpenHelper(this);

        Button Menubutton = (Button) findViewById(R.id.btLogin);
        Menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final EditText etUsername = (EditText) findViewById(R.id.etNewUsername);
                    final EditText etPassword = (EditText) findViewById(R.id.etPassword);

                    String strUsername = etUsername.getText().toString();
                    String strPass = etPassword.getText().toString();

                 if (v.getId() == R.id.btLogin) {

                     // if there are empty fields - type
                    if ( strUsername.equals("") || strPass.equals("")){
                         Toast.makeText(getApplicationContext(),"יש להזין את כל השדות",Toast.LENGTH_LONG)
                                 .show();
                     }
                     // if it's users exist in system and it's not users password - type again
                     else {
                        /* if (!db.confirmUserPassword(strUsername,strPass) && (strUsername != null || strPass!= null) && db.selectUserByUsername(strUsername)){
                         Toast.makeText(getApplicationContext(), "הסיסמא או שם המשתמש אינם נכונים, אנא הקלד שנית", Toast.LENGTH_LONG).show();
                    */ }
                     // find user in db - if found: great!
                     if (db.IsUsername(strUsername)){
                         Toast.makeText(getApplicationContext(),"USER EXISTS",Toast.LENGTH_LONG).show();
                         // navigate to main menu
                         Intent MenuIntent = new Intent(MainActivity.this, MainMenu.class);
                         MenuIntent.putExtra(Intent.EXTRA_TEXT, strUsername); //PASS USER
                         startActivity(MenuIntent);
                     }
                     if (!db.IsUsername(strUsername) && (strUsername!=null || strPass !=null) ){ // if didnt found :(
                         Toast.makeText(getApplicationContext(),"NOT EXIST",Toast.LENGTH_LONG).show();
                     }
                        //}

                 }

             }
        });

        Button Registerbutton = (Button) findViewById(R.id.btUpdateUser);
        Registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent NewUserIntent = new Intent (MainActivity.this,NewUserForm.class);
                startActivity(NewUserIntent);
            }
        });


            }

}
