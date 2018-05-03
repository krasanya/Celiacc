package il.non.celiacc;

import android.app.ProgressDialog;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/////////////////

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import il.non.celiacc.Users.NewUserForm;

//import il.non.celiacc.Users.NewUserForm;


public class MainActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button Menubutton;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        firebaseAuth= FirebaseAuth.getInstance();
        //checking if there is already a user loged in, if there is it will open the main menu automatically
//        if (firebaseAuth.getCurrentUser()!=null){
//            finish();
//            Intent ExitIntent = new Intent (MainActivity.this,MainMenu.class);
//            startActivity(ExitIntent);
//        }

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        progressDialog = new ProgressDialog(this);

        Menubutton = (Button) findViewById(R.id.btLogin);
        //sending to log in method if the user click on the log in button
        Menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.btLogin) {
                    UserLogin();
                }

            }
        });

        //sends the user to the registration button
        Button Registerbutton = (Button) findViewById(R.id.btUpdateUser);
        Registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent NewUserIntent = new Intent (MainActivity.this,NewUserForm.class);
                startActivity(NewUserIntent);
            }
        });


    }
    //a method that checks if the user is registered
    private void UserLogin(){
        String strUsername = etUsername.getText().toString();
        String strPass = etPassword.getText().toString();

        // checking if there are empty fields
        if ( strUsername.equals("") || strPass.equals("")){
            Toast.makeText(getApplicationContext(),"יש להזין את כל השדות",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("נא להמתין");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(strUsername,strPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //if details are correct- navigate to main menu
                            Intent MenuIntent = new Intent(MainActivity.this, MainMenu.class);
                            startActivity(MenuIntent);
                        }else{
                            Toast.makeText(getApplicationContext(),"הדוא''ל או הסיסמא שגויים",Toast.LENGTH_LONG).show();
                            progressDialog.cancel();
                            //**********************************
                            //TODO: an option to reset password
                            //**********************************
                        }
                    }
                });
    }//UserLogin
    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, MainMenu.class));
        }
    }

}

