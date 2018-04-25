package il.non.celiacc;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class tabf extends AppCompatActivity {
    MySqliteOpenHelper db;
    public static ArrayList<HashMap<String,String>> products = new ArrayList<HashMap<String,String>>();
    public static ArrayList<String> AllProducts = new  ArrayList<String>();
    public static ArrayList<String> AllManu = new  ArrayList<String>();
    private Button nu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabf);
        db = new MySqliteOpenHelper(this);
        AllProducts = db.getProductName();
        AllManu = db.getManuName();
        final ArrayAdapter<String> a =  new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,AllProducts);
        final ArrayAdapter<String> b =  new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,AllManu);

        final AutoCompleteTextView etyalla = (AutoCompleteTextView) findViewById(R.id.etPrody);
        etyalla.setAdapter(a);
        final AutoCompleteTextView etmanu = (AutoCompleteTextView) findViewById(R.id.etManu);
        etmanu.setAdapter(b);
        nu = (Button) findViewById(R.id.btFine);
        nu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btFine){
                    products.clear();
                    // DropDown values
                    final AutoCompleteTextView etmanu = (AutoCompleteTextView) findViewById(R.id.etManu);
                    final AutoCompleteTextView etyalla = (AutoCompleteTextView) findViewById(R.id.etPrody);
                    etyalla.setAdapter(a);
                    // To String
                    String go = etyalla.getText().toString();
                    String go2 = etmanu.getText().toString();
                    // Fill DropDown List
                    products = db.getProducts(go,go2);
                    if (products.isEmpty()){
                        Toast.makeText(getApplicationContext(), "המוצר או היצרן אינו נמצא במאגר", Toast.LENGTH_LONG).show();
                    }
                    else{
                       // Log.d("PRODUCTS", "Value: " + products.size());
                        //for (int i=0 ; i< products.size();i++){
//                            Log.d("PRODUCTS",products.get(i).get("Product"));
//                            Log.d("PRODUCTS",products.get(i).get("Barcode"));
                          //  }

                        Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
                        //Go to result list
                        onSelectSearchClick();

                    }
                }}  });
                // On EXIT
                Button exit = (Button) findViewById(R.id.btExitToMain);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent SearchIntent = new Intent (tabf.this,MainMenu.class);
                        startActivity(SearchIntent);
                    }
    });
    }

    public void onSelectSearchClick(){
        startActivityForResult(new Intent(this,ProductSearchResult.class),1);
    }
}
