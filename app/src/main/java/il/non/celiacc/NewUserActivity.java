package il.non.celiacc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class NewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        final EditText etEmail = (EditText)  findViewById(R.id.etEmail);
        final EditText etUsername =(EditText)  findViewById(R.id.etUsername);
        final EditText etFirstname = (EditText)  findViewById(R.id.etFirstname);
        final EditText etLastname = (EditText)  findViewById(R.id.etLastname);
        final EditText etPass = (EditText) findViewById(R.id.etPass);
        final CheckBox cbIsmember = (CheckBox) findViewById(R.id.cbIsmember);
        final Button btRegister = (Button) findViewById(R.id.btRegister);
    }
}
