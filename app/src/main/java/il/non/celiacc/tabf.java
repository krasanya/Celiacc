package il.non.celiacc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class tabf extends AppCompatActivity {
    MySqliteOpenHelper db;
    public static ArrayList<HashMap<String,String>> products = new ArrayList<HashMap<String,String>>();

    private Button nu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabf);
        db = new MySqliteOpenHelper(this);

        nu = (Button) findViewById(R.id.btFine);
        nu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btFine){
                    products.clear();
                    final EditText etyalla = (EditText) findViewById(R.id.etPrody);
                    String go = etyalla.getText().toString();
                    products = db.getProducts(go);
                    if (products.isEmpty()){
                        Toast.makeText(getApplicationContext(), "המוצר אינו נמצא במאגר", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Log.d("PRODUCTS", "Value: " + products.size());
                        for (int i=0 ; i< products.size();i++){
                            Log.d("PRODUCTS",products.get(i).get("Product"));
                            Log.d("PRODUCTS",products.get(i).get("IsGlutenFree"));

                            }

                        Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
                        onSelectSearchClick();

                    }
                }
            }
        });
    }

    public void onSelectSearchClick(){
        startActivityForResult(new Intent(this,ProductSearchResult.class),1);
    }
}
