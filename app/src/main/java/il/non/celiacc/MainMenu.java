package il.non.celiacc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);

        // get the text from MainActivity
        Intent intent = getIntent();
        final String textUsername = intent.getStringExtra(Intent.EXTRA_TEXT);


        Button Barcodebutton = (Button) findViewById(R.id.buttonBarcode);
        Button Updatebutton = (Button) findViewById(R.id.buttonUpdateUser);
        Button Exitbutton = (Button) findViewById(R.id.buttonExitMain);
        Button Searchbutton = (Button) findViewById(R.id.buttonSearch);
        Button ButtonTry = (Button) findViewById(R.id.buttonSearchCat);
        Barcodebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BarcodeIntent = new Intent (MainMenu.this,BarcodeScan.class);
                //BarcodeIntent.putExtra(Intent.EXTRA_TEXT, textUsername); //PASS USER
                startActivity(BarcodeIntent);
            }
        });
        Updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent UpdateIntent = new Intent (MainMenu.this,UpdateUser.class);
                UpdateIntent.putExtra(Intent.EXTRA_TEXT, textUsername); //PASS USER
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

        ButtonTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SearchIntent = new Intent (MainMenu.this,Cat_Grid.class);
                startActivity(SearchIntent);
            }
        });


    }
}
