package il.non.celiacc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;


public class MyAdapter extends ArrayAdapter<HashMap<String, String>> {

    public ArrayList<HashMap<String, String>> myList;
    Activity activity;
    MySqliteOpenHelper db = new MySqliteOpenHelper(getContext());

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
            convertView.setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT);
            viewHolder.product_name = (TextView) convertView.findViewById(R.id.tvProdName);
            viewHolder._Barcode = (TextView) convertView.findViewById(R.id.tvIsGlutenFree);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder) convertView.getTag();
        }

        viewHolder.product_name.setText(map.get("Product"));
        viewHolder._Barcode.setText(map.get("IsGlutenFree"));

        // ON CLICK _ SHOW INFORMATION
        viewHolder.product_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Title="";
                String FirstLingMessage = "";
                String SecondLingMessage ="";
                String ThirdLingMessage = "";
                String BarcodeC="";

                Cursor c = db.findProductBarcodeCursor(map.get("Barcode").toString());
                if (c==null)
                {
                    BarcodeC="Not Found";
                }
                else{
                    c.moveToFirst();
                    BarcodeC= c.getString(c.getColumnIndex("IsGlutenFree"));
                }

                //building an alert dialog
                AlertDialog.Builder Results = new AlertDialog.Builder(getContext());

                //setting the content of the alert dialog
                if (BarcodeC.equals("Not Found")){
                    Title=map.get("Barcode").toString();
                    FirstLingMessage="המוצר שביקשת אינו נמצא במאגר, ועל כן אין מידע נוסף לגביו";
                    SecondLingMessage="במידה ותרצה תוכל לפנות לעמותה והנושא יבדק.";
                }
                else
                {
                    FirstLingMessage="שם המוצר: "+c.getString(c.getColumnIndex("ProductName"));
                    SecondLingMessage="יצרן: "+c.getString(c.getColumnIndex("Manufacturer"));
                    if (BarcodeC.equals("N")){
                        ThirdLingMessage="";
                    }
                    else ThirdLingMessage="תאריך אישור: "+c.getString(c.getColumnIndex("dateValid"));
                }

                //setting the title of the alert dialog
                if (BarcodeC.equals("Y")) {
                    Title = "המוצר אינו מכיל גלוטן";
                    //showInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
                }
                else if (BarcodeC.equals("N")) {
                    Title = "המוצר מכיל גלוטן";
                    //showInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
                }
                else if (BarcodeC.equals("M")) {
                    Title="המוצר עלול להכיל גלוטן";
                   // showInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
                }

                //poping the specific alert according to the barcode

                Results.setTitle(Title);
                Results.setMessage(FirstLingMessage + "\n" + SecondLingMessage + "\n" + ThirdLingMessage);
                Results.setCancelable(false);

                //defining the positive button that will let the user scan again
                Results.setNegativeButton(
                        "חזור לחיפוש",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent RegIntent = new Intent(getContext(), tabf.class );
                                activity.startActivity(RegIntent);
                            }
                        });
                Results.setPositiveButton(
                        "חזור לרשימת התוצאות",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent RegIntent = new Intent(getContext(), ProductSearchResult.class);
                                activity.startActivity(RegIntent);
                                dialog.cancel();
                            }
                        });

                // change color of the buttons in alert dialog
                final AlertDialog showInfo = Results.create();

                showInfo.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                        showInfo.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                    }
                });

                //setting the colour of the alert dialog
                if (BarcodeC.equals("Y")) {
                    showInfo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    showInfo.getWindow().setBackgroundDrawableResource(R.color._GreenLight);
                }
                else if (BarcodeC.equals("N")) {
                   showInfo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                showInfo.getWindow().setBackgroundDrawableResource(R.color._red);
                }
                else if (BarcodeC.equals("M")) {
                    showInfo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                showInfo.getWindow().setBackgroundDrawableResource(R.color._yellow);
                }

                showInfo.show();

            }
        });
        return convertView;

        }

    public class ItemViewHolder {
        TextView product_name;
        TextView _Barcode;

    }
}

