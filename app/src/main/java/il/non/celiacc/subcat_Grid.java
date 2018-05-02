package il.non.celiacc;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class subcat_Grid extends ListActivity {
    MySqliteOpenHelper db = new MySqliteOpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get the text from MainActivity
        Intent intent = getIntent();
        String textSubCategory = intent.getStringExtra(Intent.EXTRA_TEXT);

        // use the text in a TextView
        ArrayList<HashMap<String, String>> temp = db.getSubCategories(textSubCategory);

        if (!temp.isEmpty()) {
            subCatAdapter adapt = new subCatAdapter(this, temp , textSubCategory);
            setListAdapter(adapt);
        }
    }
 }
