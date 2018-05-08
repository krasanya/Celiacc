package il.non.celiacc.Products;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import il.non.celiacc.MainMenu;
import il.non.celiacc.MySqliteOpenHelper;
import il.non.celiacc.Products.ProductAdapter;
import il.non.celiacc.Products.SearchProductActivity;
import il.non.celiacc.R;

public class ProductFragment extends Fragment {

//    MySqliteOpenHelper db =  new MySqliteOpenHelper(getActivity());
//    public static ArrayList<HashMap<String,String>> products = new ArrayList<HashMap<String,String>>();
//    public static ArrayList<String> AllProducts = new  ArrayList<String>();
//    public static ArrayList<String> AllManu = new  ArrayList<String>();
//    private Button searchNow;
//
//    @Nullable
////    @Override
////    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
////        final View view = inflater.inflate(R.layout.fragment_product, container, false);
//
////       AllProducts = db.getProductName();
////        AllManu = db.getManuName();
////
////        final ArrayAdapter<String> a =  new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,AllProducts);
////        final ArrayAdapter<String> b =  new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,AllManu);
////
////        final AutoCompleteTextView etyalla = (AutoCompleteTextView) view.findViewById(R.id.etPrody);
////        etyalla.setAdapter(a);
////        final AutoCompleteTextView etmanu = (AutoCompleteTextView) view.findViewById(R.id.etManu);
////        etmanu.setAdapter(b);
////
////        searchNow = (Button) view.findViewById(R.id.btFine);
////        searchNow.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (v.getId() == R.id.btFine){
////                    products.clear();
////                    // DropDown values
////                    final AutoCompleteTextView etmanu = (AutoCompleteTextView) view.findViewById(R.id.etManu);
////                    final AutoCompleteTextView etyalla = (AutoCompleteTextView) view.findViewById(R.id.etPrody);
////                    etyalla.setAdapter(a);
////                    // To String
////                    String go = etyalla.getText().toString();
////                    String go2 = etmanu.getText().toString();
////                    // Fill DropDown List
////                    products = db.getProducts(go,go2);
////                    if (products.isEmpty()){
////                        Toast.makeText(getContext(), "המוצר או היצרן אינו נמצא במאגר", Toast.LENGTH_LONG).show();
////                    }
////                    else{
////                        Log.d("PRODUCTS", "Value: " + products.size());
////                        for (int i=0 ; i< products.size();i++){
////                            Log.d("PRODUCTS",products.get(i).get("Product"));
////                            Log.d("PRODUCTS",products.get(i).get("Barcode"));
////                        }
////                        Toast.makeText(getContext(), "OK", Toast.LENGTH_LONG).show();
////                    }
////                    RecyclerView recyclerView = view.findViewById(R.id.rvProducts);
////                    ProductAdapter productAdapter = new ProductAdapter(getContext(), (ProductAdapter.ProductAdapterOnClickHandler) getActivity());
////                  //  productAdapter.setProductData(products);
////                    GridLayoutManager gridLayoutManager = new GridLayoutManager(
////                            getContext(),1, LinearLayoutManager.VERTICAL, false);
////                    recyclerView.setLayoutManager(gridLayoutManager);
////                    recyclerView.setAdapter(productAdapter);
////                    recyclerView.setHasFixedSize(true);
////                }
////            }
////        });
////
////        Button button = view.findViewById(R.id.btExitToMain);
////        button.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intentExit = new Intent(getContext(), MainMenu.class);
////                startActivity(intentExit);
////            }
////        });
////
////
////        return view;
////    }
//
//    public void setData(ArrayList<String> AllProducts ,ArrayList<String> AllManu,ArrayList<HashMap<String,String>> products){
//        this.AllProducts = AllProducts;
//        this.AllManu = AllManu;
//        this.products = products;
//
//    }
//    public void onClick(int productPosition) {
//        Log.d("ENTER","OK");
//        String Title="";
//        String FirstLingMessage = "";
//        String SecondLingMessage ="";
//        String ThirdLingMessage = "";
//        String BarcodeC="";
//
//        Cursor c = db.findProductBarcodeCursor(products.get(productPosition).get("Barcode").toString());
//        if (c==null)
//        {
//            BarcodeC="Not Found";
//        }
//        else{
//            c.moveToFirst();
//            BarcodeC= c.getString(c.getColumnIndex("IsGlutenFree"));
//        }
//
//        //building an alert dialog
//        AlertDialog.Builder Results = new AlertDialog.Builder(getActivity());
//
//        //setting the content of the alert dialog
//        if (BarcodeC.equals("Not Found")){
//            Title=products.get(productPosition).get("Barcode").toString();
//            FirstLingMessage="המוצר שביקשת אינו נמצא במאגר, ועל כן אין מידע נוסף לגביו";
//            SecondLingMessage="במידה ותרצה תוכל לפנות לעמותה והנושא יבדק.";
//        }
//        else
//        {
//            FirstLingMessage="שם המוצר: "+c.getString(c.getColumnIndex("ProductName"));
//            SecondLingMessage="יצרן: "+c.getString(c.getColumnIndex("Manufacturer"));
//            if (BarcodeC.equals("N")){
//                ThirdLingMessage="";
//            }
//            else ThirdLingMessage="תאריך אישור: "+c.getString(c.getColumnIndex("dateValid"));
//        }
//
//        //setting the title of the alert dialog
//        if (BarcodeC.equals("Y")) {
//            Title = "המוצר אינו מכיל גלוטן";
//        }
//        else if (BarcodeC.equals("N")) {
//            Title = "המוצר מכיל גלוטן";
//        }
//        else if (BarcodeC.equals("M")) {
//            Title="המוצר עלול להכיל גלוטן";
//        }
//
//        //poping the specific alert according to the barcode
//        Results.setTitle(Title);
//        Results.setMessage(FirstLingMessage + "\n" + SecondLingMessage + "\n" + ThirdLingMessage);
//        Results.setCancelable(false);
//
//        Results.setNegativeButton(
//                "חזור לרשימת התוצאות",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//
//        // change color of the buttons in alert dialog
//        final AlertDialog showInfo = Results.create();
//        showInfo.setOnShowListener( new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface arg0) {
//                showInfo.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
//            }
//        });
//
//        //setting the colour of the alert dialog
//        if (BarcodeC.equals("Y")) {
//            showInfo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            showInfo.getWindow().setBackgroundDrawableResource(R.color._GreenLight);
//        }
//        else if (BarcodeC.equals("N")) {
//            showInfo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            showInfo.getWindow().setBackgroundDrawableResource(R.color._red);
//        }
//        else if (BarcodeC.equals("M")) {
//            showInfo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            showInfo.getWindow().setBackgroundDrawableResource(R.color._yellow);
//        }
//
//        showInfo.show();
//
//    }
}


