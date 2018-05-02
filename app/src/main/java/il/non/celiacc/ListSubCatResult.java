package il.non.celiacc;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

public class ListSubCatResult extends ListActivity {
    //private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    MySqliteOpenHelper db = new MySqliteOpenHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get the text from MainActivity
        Intent intent2 = getIntent();
        String Subtext = intent2.getStringExtra(Intent.EXTRA_TEXT);

        // use the text
        ArrayList<HashMap<String, String>> subProducts = db.getSubProductList(Subtext);
        subCatListAdapter adapt = new subCatListAdapter(this,subProducts,Subtext);
        setListAdapter(adapt);
    }
}
