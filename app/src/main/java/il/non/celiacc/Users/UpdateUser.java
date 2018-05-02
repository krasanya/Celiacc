package il.non.celiacc.Users;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import il.non.celiacc.MainMenu;
import il.non.celiacc.MySqliteOpenHelper;
import il.non.celiacc.R;


public class UpdateUser extends AppCompatActivity {
    MySqliteOpenHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        db = new MySqliteOpenHelper(this);
        Intent intent = getIntent();
        final String textUsername = intent.getStringExtra(Intent.EXTRA_TEXT);

        //setHint()
        final String EmailOld = db.getEmailByUsername(textUsername);
        String UsernameOld = db.getUsernameFromUsername(textUsername);
        String FnameOld = db.getFirstNameFromUsername(textUsername);
        String LnameOld = db.getLastnameFromUsername(textUsername);
        String PassOld = db.getPassFromUsername(textUsername);
        TextView password = (TextView) findViewById(R.id.etNewPass);
        TextView password2 = (TextView) findViewById(R.id.etNewPassAgain);
        ((TextView) findViewById(R.id.etEmail)).setText(EmailOld);
        ((TextView) findViewById(R.id.etNewUsername)).setText(UsernameOld);
        ((TextView) findViewById(R.id.etNewFirstname)).setText(FnameOld);
        ((TextView) findViewById(R.id.etNewLastname)).setText(LnameOld);
        ((TextView) findViewById(R.id.etNewPass)).setText(PassOld);
        ((TextView) findViewById(R.id.etNewPassAgain)).setText(PassOld);
        password2.setTypeface(Typeface.DEFAULT);
        password2.setTransformationMethod(new PasswordTransformationMethod());


        final Button btUpdate = (Button) findViewById(R.id.btUpdateUser);
        final Button btBack = (Button) findViewById(R.id.btbackToMenu);
        btUpdate.setOnClickListener(new View.OnClickListener() {
        //on click "Update"
        public void onClick(View v) {
            if (v.getId() == R.id.btUpdateUser) {
                final EditText etmail = (EditText) findViewById(R.id.etEmail);
                final EditText etuser = (EditText) findViewById(R.id.etNewUsername);
                final EditText etfname = (EditText) findViewById(R.id.etNewFirstname);
                final EditText etlname = (EditText) findViewById(R.id.etNewLastname);
                final EditText etphone = (EditText) findViewById(R.id.etNewPhone);

                final EditText etPass = (EditText) findViewById(R.id.etNewPass);
                final EditText etPass2 = (EditText) findViewById(R.id.etNewPassAgain);
                //int flag = 0;
                final String strmail = etmail.getText().toString();
                final String struser = etuser.getText().toString();
                final String strfname = etfname.getText().toString();
                final String strlname = etlname.getText().toString();
               // final String strphone = etphone.getText().toString();

                String strPass = etPass.getText().toString();
                String strPass2 = etPass2.getText().toString();
                final String strEmailValidate = etmail.getText().toString().trim();

                // EMAIL VALIDATION []@[].[]
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (!(strEmailValidate.matches(emailPattern)))
                {
                    Toast.makeText(getApplicationContext(),"כתובת הדואל אינה תקינה", Toast.LENGTH_SHORT).show();
                }
                //PASSWORD VALIDATION - 6 CHARACTERS AT LEAST , ONE ALPHABET ONE NUMBER AND ONE SPECIAL CHARACTER AT LEAST
                else if ((etPass.getText().toString().length()< 6 && !isValidPassword(etPass.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "על הסיסמא להכיל 6 תווים לפחות, אות אחת לפחות , ספרה אחת לפחות וסימן מיוחד אחד לפחות", Toast.LENGTH_LONG).show();
                }

                // if there is such Email already - type again
                else if (db.IsEmail(strmail) && !(db.getEmailByUsername(struser).equals(strmail))) {
                    Toast.makeText(getApplicationContext(), "דואר אלקטרוני זה כבר קיים במערכת", Toast.LENGTH_LONG).show();
                }
                // if there is such user already - type again
                else if (db.IsUsername(struser) && !struser.equals(textUsername) && struser != null) {
                    Toast.makeText(getApplicationContext(), "שם משתמש זה כבר קיים במערכת", Toast.LENGTH_LONG).show();
                 }
                // if passwords doesnt exist - type
                else if (strPass.equals("")) {
                    Toast.makeText(getApplicationContext(), "אנא הקלד סיסמא", Toast.LENGTH_LONG)
                            .show();
                }
                // if passwords doesnt match - type again
                else if (!strPass.equals(strPass2)) {
                    Toast.makeText(getApplicationContext(), "הסיסמאות אינן זהות אנא נסה שנית", Toast.LENGTH_LONG)
                            .show();
                }
                // if passwords ok - move on
                else if (strPass.equals(strPass2)) {
                    db.UpdateUser(EmailOld, strmail , struser ,strfname ,strlname ,strPass );
                    Intent UpdateIntent = new Intent(UpdateUser.this, MainMenu.class);
                    UpdateIntent.putExtra(Intent.EXTRA_TEXT, textUsername); //PASS USER
                    startActivity(UpdateIntent);
                }
            }
        }
    });
        btBack.setOnClickListener(new View.OnClickListener() {
            //on click "BAck to main menu"
            public void onClick(View v) {
                Intent UpdateIntent = new Intent (UpdateUser.this,MainMenu.class);
                UpdateIntent.putExtra(Intent.EXTRA_TEXT, textUsername); //PASS USER
                startActivity(UpdateIntent);
            }
            });
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
