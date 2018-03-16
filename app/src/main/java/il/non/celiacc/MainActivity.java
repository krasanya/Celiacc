package il.non.celiacc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button btLogin = (Button) findViewById(R.id.btLogin);
        final Button btRegister = (Button) findViewById(R.id.btRegister);


        Button Menubutton = (Button) findViewById(R.id.btLogin);
        Menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MenuIntent = new Intent (MainActivity.this,MainMenu.class);
                startActivity(MenuIntent);
            }
        });

        Button Registerbutton = (Button) findViewById(R.id.btRegister);
        Menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewUserIntent = new Intent (MainActivity.this,NewUserActivity.class);
                startActivity(NewUserIntent);
            }
        });


    }
}
