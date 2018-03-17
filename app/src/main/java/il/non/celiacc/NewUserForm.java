package il.non.celiacc;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
///////////
public class NewUserForm extends AppCompatActivity {

    //db
    MySqliteOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_form);

        db = new MySqliteOpenHelper(this);

        final Button btRegister = (Button) findViewById(R.id.btRegister);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            //on click "Register"
            public void onClick(View v) {
                if (v.getId() == R.id.btRegister) {
                    final EditText etEmail = (EditText) findViewById(R.id.etEmail);
                    final EditText etUsername = (EditText) findViewById(R.id.etUsername);
                    final EditText etFirstname = (EditText) findViewById(R.id.etFirstname);
                    final EditText etLastname = (EditText) findViewById(R.id.etLastname);
                    final EditText etPass = (EditText) findViewById(R.id.etPass);
                    final EditText etPass2 = (EditText) findViewById(R.id.etPass2);

                    String strEmail = etEmail.getText().toString();
                    String strUsername = etUsername.getText().toString();
                    String strFirstname = etFirstname.getText().toString();
                    String strLastname = etLastname.getText().toString();
                    String strPass = etPass.getText().toString();
                    String strPass2 = etPass2.getText().toString();

                    // if there are empty fields - type
                    if (strEmail.equals("") || strUsername.equals("") || strFirstname.equals("") || strLastname.equals("") || strPass.equals("")) {
                        Toast.makeText(getApplicationContext(), "יש להזין את כל השדות", Toast.LENGTH_LONG).show();
                    }
                    // if there is such user already - type again
                    if (db.selectUserByUsername(strUsername) && strUsername!=null){
                        Toast.makeText(getApplicationContext(), "שם משתמש זה תפוס", Toast.LENGTH_LONG).show();
                   }
                   if (db.selectUserByEmail(strEmail) && strEmail!=null){
                        Toast.makeText(getApplicationContext(), "משתמש עם דואר אלקטרוני זה כבר קיים במערכת", Toast.LENGTH_LONG).show();
                    }
                    // if passwords doesnt match - type again
                    if (!strPass.equals(strPass2)) {
                        Toast.makeText(getApplicationContext(), "הסיסמאות אינן זהות אנא נסה שנית", Toast.LENGTH_LONG).show();
                    }
                    // if passwords ok - move on
                    if (strPass.equals(strPass2)) {
                        // insert to db
                        db.addUser(strEmail, strUsername, strFirstname, strLastname, strPass);
                    }
                    // navigate to main menu
                    Intent RegIntent = new Intent(NewUserForm.this, MainMenu.class);
                    startActivity(RegIntent);
                }


            }

        });



    }
}
