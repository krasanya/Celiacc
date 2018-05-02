package il.non.celiacc.Users;
//package il.non.celiacc.Users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import il.non.celiacc.MainMenu;
import il.non.celiacc.R;

///////////
public class NewUserForm extends AppCompatActivity {
    private EditText etEmail ;
    private EditText etUsername;
    private EditText etFirstname ;
    private EditText etLastname;
    private EditText etPass ;
    private EditText etPass2 ;
    private CheckBox isMember;
    private Button btRegister;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_form);

        firebaseAuth= FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);

        btRegister = findViewById(R.id.btRegister);
        //defining the edit texts according to the new user form
        etEmail =  findViewById(R.id.etEmail);
        etUsername =  findViewById(R.id.etUsername);
        etFirstname = findViewById(R.id.etFirstname);
        etLastname =  findViewById(R.id.etLastname);
        etPass =  findViewById(R.id.etPass);
        etPass2 = findViewById(R.id.etPass2);
        isMember=  findViewById(R.id.cbIsmember);


        //when the user presses the register button it will call the register method
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            //on click "Register"
            public void onClick(View v) {
                if (v == btRegister) {
                    RegisterUser();
                }
            }
        });


    }
    private void RegisterUser(){
        //retrieving the data from the edit text
        String strEmail = etEmail.getText().toString();
        String strUsername = etUsername.getText().toString();
        String strFirstname = etFirstname.getText().toString();
        String strLastname = etLastname.getText().toString();
        String strPass = etPass.getText().toString();
        String strPass2 = etPass2.getText().toString();


        // if there are empty fields - type
        if (strEmail.equals("") || strUsername.equals("") || strFirstname.equals("") || strLastname.equals("") || strPass.equals("")) {
            Toast.makeText(getApplicationContext(), "יש להזין את כל השדות", Toast.LENGTH_LONG).show();
            return;
        }
        //if email is not valid
        if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            Toast.makeText(getApplicationContext(), "יש להזין אימייל תקין", Toast.LENGTH_LONG).show();
            return;
        }
        // if passwords doesnt match - type again
        if (!strPass.equals(strPass2)) {
            Toast.makeText(getApplicationContext(), "הסיסמאות אינן זהות אנא נסה שנית", Toast.LENGTH_LONG).show();
            return;
        }
        //password is required to be at least 6 characters
        if (strPass.length() < 6) {
            Toast.makeText(getApplicationContext(), "יש להזין סיסמא בעלת 6 ספרות לפחות", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("נא להמתין");
        progressDialog.show();
        //adding new user by email and password to firebase
        //if validation is ok
        firebaseAuth.createUserWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "נרשמת בהצלחה", Toast.LENGTH_LONG).show();
                    //save all the user info to the database
                    saveUserInfo();
                    // navigate to main menu
                    Intent RegIntent = new Intent(NewUserForm.this, MainMenu.class);
                    startActivity(RegIntent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "ההרשמה נכשלה, בבקשה נסה שנית", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }
            }
        });


    }//registerUser


    private void saveUserInfo(){

        String strEmail = etEmail.getText().toString();
        String strUsername = etUsername.getText().toString();
        String strFirstname = etFirstname.getText().toString();
        String strLastname = etLastname.getText().toString();
        String strPass = etPass.getText().toString();
        Boolean strMember = isMember.isChecked();

        DatabaseReference databaseReferencechild;
        databaseReferencechild= databaseReference.child("users");
        //creating an object with the inserted data
        User userInfo = new User(strEmail,strUsername,strFirstname,strLastname,strPass,strMember,0,null,null);
        //adding the object to the users "table" in the firebase
        databaseReferencechild.push().setValue(userInfo);

        Toast.makeText(getApplicationContext(), "נתונייך נרשמו במערכת", Toast.LENGTH_LONG).show();
    }//saveUserInfo
}


//package il.non.celiacc;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
/////////////
//public class NewUserForm extends AppCompatActivity {
//
//    //db
//    MySqliteOpenHelper db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_new_user_form);
//
//        db = new MySqliteOpenHelper(this);
//
//        final Button btRegister = (Button) findViewById(R.id.btUpdateUser);
//
//        btRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            //on click "Register"
//            public void onClick(View v) {
//                if (v.getId() == R.id.btUpdateUser) {
//                    final EditText etEmail = (EditText) findViewById(R.id.etEmail);
//                    final EditText etUsername = (EditText) findViewById(R.id.etNewUsername);
//                    final EditText etFirstname = (EditText) findViewById(R.id.etNewFirstname);
//                    final EditText etLastname = (EditText) findViewById(R.id.etNewLastname);
//                    final EditText etPass = (EditText) findViewById(R.id.etNewPass);
//                    final EditText etPass2 = (EditText) findViewById(R.id.etPass2);
//
//                    String strEmail = etEmail.getText().toString();
//                    final String strEmailValidate = etEmail.getText().toString().trim();
//                    String strUsername = etUsername.getText().toString();
//                    String strFirstname = etFirstname.getText().toString();
//                    String strLastname = etLastname.getText().toString();
//                    String strPass = etPass.getText().toString();
//                    String strPass2 = etPass2.getText().toString();
//
//                    // EMAIL VALIDATION []@[].[]
//                    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//                    if (!(strEmailValidate.matches(emailPattern)))
//                    {
//                        Toast.makeText(getApplicationContext(),"כתובת הדואל אינה תקינה", Toast.LENGTH_SHORT).show();
//                    }
//                    //PASSWORD VALIDATION - 6 CHARACTERS AT LEAST , ONE ALPHABET ONE NUMBER AND ONE SPECIAL CHARACTER AT LEAST
//                    else if ((etPass.getText().toString().length()< 6 && !isValidPassword(etPass.getText().toString()))) {
//                        Toast.makeText(getApplicationContext(), "על הסיסמא להכיל 6 תווים לפחות, אות אחת לפחות , ספרה אחת לפחות וסימן מיוחד אחד לפחות", Toast.LENGTH_LONG).show();
//                    }
//
//                    // if there are empty fields - type
//                    else if (strEmail.equals("") || strUsername.equals("") || strFirstname.equals("") || strLastname.equals("") || strPass.equals("")) {
//                        Toast.makeText(getApplicationContext(), "יש להזין את כל השדות", Toast.LENGTH_LONG).show();
//                    }
//                    // if there is such user already - type again
//                    else if (db.IsUsername(strUsername) && strUsername!=null){
//                        Toast.makeText(getApplicationContext(), "שם משתמש זה תפוס", Toast.LENGTH_LONG).show();
//                   }
//                   else if (db.IsEmail(strEmail) && strEmail!=null){
//                        Toast.makeText(getApplicationContext(), "משתמש עם דואר אלקטרוני זה כבר קיים במערכת", Toast.LENGTH_LONG).show();
//                    }
//                    // if passwords doesnt match - type again
//                    else if (!strPass.equals(strPass2)) {
//                        Toast.makeText(getApplicationContext(), "הסיסמאות אינן זהות אנא נסה שנית", Toast.LENGTH_LONG).show();
//                    }
//                    // if passwords ok - move on
//                    else if (strPass.equals(strPass2)) {
//                        // insert to db
//                        db.addUser(strEmail, strUsername, strFirstname, strLastname, strPass);
//                        // navigate to main menu
//                        Intent RegIntent = new Intent(NewUserForm.this, MainMenu.class);
//                        RegIntent.putExtra(Intent.EXTRA_TEXT, strUsername); //PASS USER
//                        startActivity(RegIntent);
//                    }
//
//
//                }
//
//
//            }
//
//        });
//
//
//    }
//
//    public static boolean isValidPassword(final String password) {
//
//        Pattern pattern;
//        Matcher matcher;
//        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
//        pattern = Pattern.compile(PASSWORD_PATTERN);
//        matcher = pattern.matcher(password);
//        return matcher.matches();
//
//    }
//}
