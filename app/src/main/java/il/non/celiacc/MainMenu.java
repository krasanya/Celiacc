package il.non.celiacc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);
        Button Barcodebutton = (Button) findViewById(R.id.buttonBarcode);
        Button Updatebutton = (Button) findViewById(R.id.buttonUpdateUser);
        Button Exitbutton = (Button) findViewById(R.id.buttonExitMain);
        Button Searchbutton = (Button) findViewById(R.id.buttonSearch);
        Barcodebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BarcodeIntent = new Intent (MainMenu.this,BarcodeScan.class);
                startActivity(BarcodeIntent);
            }
        });
        Updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent UpdateIntent = new Intent (MainMenu.this,UpdateUser.class);
                startActivity(UpdateIntent);
            }
        });
        Exitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ExitIntent = new Intent (MainMenu.this,MainActivity.class);
                startActivity(ExitIntent);
            }
        });
        Searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SearchIntent = new Intent (MainMenu.this,tabf.class);
                startActivity(SearchIntent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
}
