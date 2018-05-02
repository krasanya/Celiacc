package il.non.celiacc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class subCatListAdapter  extends ArrayAdapter<HashMap<String, String>> {
    public ArrayList<HashMap<String, String>> myList;
    Activity activity;
    MySqliteOpenHelper db = new MySqliteOpenHelper(getContext());
    String Subtext = "";
    public subCatListAdapter(Activity activity, ArrayList<HashMap<String, String>> myList,String Subtext){
        super(activity, R.layout.list_sub_cat_result, myList);
        this.activity = activity;
        this.myList = myList;
        this.Subtext = Subtext;
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
        subCatListAdapter.ItemViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new subCatListAdapter.ItemViewHolder();
            convertView = activity.getLayoutInflater().inflate(R.layout.list_sub_cat_result, null, true);
            convertView.setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT);
            viewHolder.product_name = (TextView) convertView.findViewById(R.id.tvProdName);
            viewHolder._Barcode = (TextView) convertView.findViewById(R.id.tvIsGlutenFree);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (subCatListAdapter.ItemViewHolder) convertView.getTag();
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
                }
                else if (BarcodeC.equals("N")) {
                    Title = "המוצר מכיל גלוטן";
                }
                else if (BarcodeC.equals("M")) {
                    Title="המוצר עלול להכיל גלוטן";
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
                            //    dialog.cancel();//WORKS
                            //    Intent RegIntent = new Intent(getContext(), subcat_Grid.class );//WORKS
                            //    activity.startActivity(RegIntent);//WORKS
                                // put the String to pass back into an Intent and close this activity
                                  Intent intent = new Intent();
                                  intent.putExtra(Intent.EXTRA_TEXT, Subtext);
                                  activity.setResult(Activity.RESULT_OK, intent);
                                   activity.finish();
                            }
                        });
                Results.setPositiveButton(
                        "חזור לרשימת התוצאות",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                // put the String to pass back into an Intent and close this activity
                                Intent intent = new Intent(getContext(), ListSubCatResult.class);
                               intent.putExtra(Intent.EXTRA_TEXT, Subtext);
                                activity.startActivity(intent);
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
