package il.non.celiacc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        final Button btRegister = (Button) findViewById(R.id.btRegister);

        btRegister.setOnClickListener(new View.OnClickListener() {
        //on click "Register"

        public void onClick(View v) {
            if (v.getId() == R.id.btRegister){

                final EditText etPass = (EditText) findViewById(R.id.etPass);
                final EditText etPass2 = (EditText) findViewById(R.id.etPassAgain);

                String strPass = etPass.getText().toString();
                String strPass2 = etPass2.getText().toString();

                // if passwords doesnt match - type again
                if (!strPass.equals(strPass2)){
                    Toast.makeText(getApplicationContext(),"הסיסמאות אינן זהות אנא נסה שנית",Toast.LENGTH_LONG)
                            .show();
                }
                // if passwords ok - move on
                    Intent RegIntent = new Intent (UpdateUser.this,MainMenu.class);
                    startActivity(RegIntent);
                }
            }


    });
    }
}
