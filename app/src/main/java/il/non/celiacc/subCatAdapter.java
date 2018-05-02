package il.non.celiacc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class subCatAdapter extends ArrayAdapter<HashMap<String, String>> {
    public ArrayList<HashMap<String, String>> myList;
    Activity activity;

    MySqliteOpenHelper db = new MySqliteOpenHelper(getContext());
    public static ArrayList<HashMap<String,String>> subProducts = new ArrayList<HashMap<String,String>>();
    String textSubCategory = "";
    public subCatAdapter(Activity activity, ArrayList<HashMap<String, String>> myList,String textSubCategory){
        super(activity, R.layout.subcat__grid, myList);
        this.activity = activity;
        this.myList = myList;
        this.textSubCategory = textSubCategory;

    }

    @Override
    public int getCount(){
        return myList.size();
    }

    @Override
    public HashMap<String,String> getItem(int position){
        return myList.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    public View getView(int position , View convertView, ViewGroup parent) {

        final HashMap<String, String> map = myList.get(position);
        final subCatAdapter.ItemViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new subCatAdapter.ItemViewHolder();
            convertView = activity.getLayoutInflater().inflate(R.layout.subcat__grid, null, true);
            convertView.setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT);
            viewHolder.sub_cat_name = (TextView) convertView.findViewById(R.id.tvsubCat);
            viewHolder.sub_cat_image = (TextView) convertView.findViewById(R.id.iv_subcat_image);
            viewHolder.exit = (Button) convertView.findViewById(R.id.btBackToCat);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (subCatAdapter.ItemViewHolder) convertView.getTag();
        }

        viewHolder.sub_cat_name.setText(map.get("subCategoryName"));
        viewHolder.sub_cat_image.setText(map.get("CategoryName"));

        // ON CLICK _ SHOW INFORMATION
        viewHolder.sub_cat_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the text to pass
                String textToPass = viewHolder.sub_cat_name.getText().toString();

                // start the SecondActivity
                Intent intent2 = new Intent(getContext(), ListSubCatResult.class);
                intent2.putExtra(Intent.EXTRA_TEXT, textToPass); //WORKS
                activity.startActivity(intent2); //WORKS

            }
        });
        //ON EXIT
        viewHolder.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentExit = new Intent(getContext(), Cat_Grid.class);
                activity.startActivity(intentExit);

            }
        });
        return convertView;

    }

    public class ItemViewHolder {
        TextView sub_cat_name;
        TextView sub_cat_image;
        Button exit;
    }
}
