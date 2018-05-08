package il.non.celiacc.Products;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import il.non.celiacc.MainMenu;
import il.non.celiacc.R;

public class SearchProductActivity extends AppCompatActivity
        implements ProductAdapter.ProductAdapterOnClickHandler{
    ArrayList<Product> p = new ArrayList<>();

    private Button searchNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_product_activity);
        final RecyclerView recyclerView = findViewById(R.id.rvProducts);
        final ProductAdapter productAdapter = new ProductAdapter(SearchProductActivity.this, SearchProductActivity.this);

        final AutoCompleteTextView etmanu = (AutoCompleteTextView) findViewById(R.id.etManu);
        final AutoCompleteTextView etyalla = (AutoCompleteTextView) findViewById(R.id.etPrody);

        searchNow = (Button) findViewById(R.id.btFine);
        searchNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    p.clear();
                    // To String
                    final String productToSearch = etyalla.getText().toString();
                    final String manuToSearch = etmanu.getText().toString();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference product = reference.child("Products");
                    Query query = product.orderByChild("ProductName").startAt(productToSearch).endAt(productToSearch+ "/uf8ff");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    Product product = new Product();
                                    product.setProductName(child.getValue(Product.class).getProductName());
                                    product.setBarcode(child.getValue(Product.class).getBarcode());
                                    product.setCategoryName(child.getValue(Product.class).getCategoryName());


                                    if (!manuToSearch.equals("")) {
                                        if (child.getValue(Product.class).getManufacturer().contains(manuToSearch)) {
                                            p.add(product);
                                        }
                                    }
                                    else {
                                            p.add(product);
                                        }
                                }

                                productAdapter.setProductData(p);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(
                                        SearchProductActivity.this, 1, LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(gridLayoutManager);
                                recyclerView.setAdapter(productAdapter);
                                recyclerView.setHasFixedSize(true);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("CANCELLED", "Error trying to get products ");
                            Toast.makeText(getApplicationContext(),
                                    "Error trying to get products", Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        });

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        //Create a new ArrayAdapter
        final ArrayAdapter<String> autoCompleteProduct = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        final ArrayAdapter<String> autoCompleteManufacturer = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        final ArrayList<String> noDuplicatesM = new ArrayList<>();
        final ArrayList<String> noDuplicatesP = new ArrayList<>();
        noDuplicatesP.clear();
        noDuplicatesM.clear();
        //Child the root before all the push() keys are found and add a ValueEventListener()
        database.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //"For each DataSnapshot *Data* in dataSnapshot, do what's inside the method.
                for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()){
                    //Get the suggestion
                    noDuplicatesP.add(suggestionSnapshot.child("ProductName").getValue(String.class));
                    noDuplicatesM.add(suggestionSnapshot.child("Manufacturer").getValue(String.class));

                    //NO DUPLICATES
                    for(String s: noDuplicatesM){
                        //This removes all strings that are equal to s in the adapter
                        for(int i=0;i < autoCompleteManufacturer.getCount();i++){
                            if(autoCompleteManufacturer.getItem(i).equals(s)){
                            autoCompleteManufacturer.remove(s);}
                        }
                        //Now that there are no more s in the ArrayAdapter, we add a single s, so now we only have one
                        autoCompleteManufacturer.add(s);
                    }
                    for(String s: noDuplicatesP){
                        //This removes all strings that are equal to s in the adapter
                        for(int i=0;i < autoCompleteProduct.getCount();i++){
                            if(autoCompleteProduct.getItem(i).equals(s)){
                                autoCompleteProduct.remove(s);}
                        }
                        autoCompleteProduct.add(s);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        etyalla.setAdapter(autoCompleteProduct);
        etmanu.setAdapter(autoCompleteManufacturer);

        Button button = findViewById(R.id.btExitToMain);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentExit = new Intent(SearchProductActivity.this, MainMenu.class);
                            startActivity(intentExit);
                        }
                    });
    }

    @Override
    public void onClick(int productPosition) {
        Log.d("ENTER","OK");
        String Title="";
        String FirstLingMessage = "";
        String SecondLingMessage ="";
        String ThirdLingMessage = "";
        String BarcodeC="";

//        Cursor c = db.findProductBarcodeCursor(products.get(productPosition).get("Barcode").toString());
//        if (c==null)
//        {
//            BarcodeC="Not Found";
//        }
//        else{
//            c.moveToFirst();
//            BarcodeC= c.getString(c.getColumnIndex("IsGlutenFree"));
//         }
//
//        //building an alert dialog
//        AlertDialog.Builder Results = new AlertDialog.Builder(SearchProductActivity.this);
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
    }

}
