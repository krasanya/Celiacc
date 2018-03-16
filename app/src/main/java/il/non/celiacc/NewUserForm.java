package il.non.celiacc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_form);

        // variables
        final EditText etEmail = (EditText)  findViewById(R.id.etEmail);
        final EditText etUsername =(EditText)  findViewById(R.id.etUsername);
        final EditText etFirstname = (EditText)  findViewById(R.id.etFirstname);
        final EditText etLastname = (EditText)  findViewById(R.id.etLastname);
        final EditText etPass = (EditText) findViewById(R.id.etPass);
        final CheckBox cbIsmember = (CheckBox) findViewById(R.id.cbIsmember);
        final Button btRegister = (Button) findViewById(R.id.btRegister);



        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                //pop up
                Toast.makeText(getApplicationContext(),"הרישום הושלם בהצלחה! ברוך הבא לאתר!",Toast.LENGTH_LONG)
                .show();
                //on click go to page
                Intent RegIntent = new Intent (NewUserForm.this,MainMenu.class);
                startActivity(RegIntent);
            }
        });



    }
}
