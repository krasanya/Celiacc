package il.non.celiacc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;


public class MyAdapter extends ArrayAdapter<HashMap<String, String>> {

    public ArrayList<HashMap<String, String>> myList;
    Activity activity;

    public MyAdapter(Activity activity, ArrayList<HashMap<String, String>> myList){
        super(activity, R.layout.prod_search_result, myList);
        this.activity = activity;
        this.myList = myList;
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

    public View getView(int position ,View convertView, ViewGroup parent) {

        final HashMap<String, String> map = myList.get(position);
        ItemViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ItemViewHolder();
            convertView = activity.getLayoutInflater().inflate(R.layout.prod_search_result, null, true);
            viewHolder.product_name = (TextView) convertView.findViewById(R.id.tvProdName);
            viewHolder.Is_gluten_free = (TextView) convertView.findViewById(R.id.tvIsGlutenFree);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder) convertView.getTag();
        }

        viewHolder.product_name.setText(map.get("Product"));
        viewHolder.Is_gluten_free.setText(map.get("IsGlutenFree"));

        viewHolder.product_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FirstLingMessage = "";
                String SecondLingMessage ="";
                String ThirdLingMessage = "";
                Toast.makeText(getContext(), "ON CLICK IS WORKING !!!!", Toast.LENGTH_LONG).show();
                AlertDialog.Builder Results = new AlertDialog.Builder(getContext());
                if (map.get("IsGlutenFree").toString().equals("Y")) {
                   // FirstLingMessage = "שם המוצר: " + c.getString(c.getColumnIndex("ProductName"));
                   // SecondLingMessage = "יצרן: " + c.getString(c.getColumnIndex("Manufacturer"));
                   // ThirdLingMessage = "תאריך אישור: " + c.getString(c.getColumnIndex("dateValid"));
                    if  (map.get("IsGlutenFree").toString().equals("N")) {


                }

            }

            }
        });
        return convertView;

        }

    public class ItemViewHolder {
        TextView product_name;
        TextView Is_gluten_free;

    }

}

