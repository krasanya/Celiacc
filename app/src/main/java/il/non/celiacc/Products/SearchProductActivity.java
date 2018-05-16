package il.non.celiacc.Products;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;

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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        final RecyclerView recyclerView = findViewById(R.id.rvProducts);
        final ProductAdapter productAdapter = new ProductAdapter(SearchProductActivity.this, SearchProductActivity.this);
        //Create a new ArrayAdapter
        final ArrayAdapter<String> autoCompleteProduct = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        final ArrayAdapter<String> autoCompleteManufacturer = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        final ArrayList<String> noDuplicatesM = new ArrayList<>();
        final ArrayList<String> noDuplicatesP = new ArrayList<>();
        autoCompleteProduct.clear();
        autoCompleteProduct.clear();
        final AutoCompleteTextView etmanu = (AutoCompleteTextView) findViewById(R.id.etManu);
        final AutoCompleteTextView etyalla = (AutoCompleteTextView) findViewById(R.id.etPrody);

        searchNow = (Button) findViewById(R.id.btFine);
        searchNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    p.clear();
                    noDuplicatesP.clear();
                    noDuplicatesM.clear();
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
                                    createProduct(child,product);
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
                            else{
                                Log.d("ALL","inside");
                                if (manuToSearch.equals("")){
                                    Log.d("ALL","inside222");
                                    FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("ProductName").addListenerForSingleValueEvent
                                            (new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Log.d("ALL","inside3333");
                                                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                                                        Product product = new Product();
                                                        createProduct(child, product);
                                                        Log.d("ALL","inside4444");
                                                        p.add(product);

                                                    }
                                                    productAdapter.setProductData(p);
                                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(
                                                            SearchProductActivity.this, 1, LinearLayoutManager.VERTICAL, false);
                                                    recyclerView.setLayoutManager(gridLayoutManager);
                                                    recyclerView.setAdapter(productAdapter);
                                                    recyclerView.setHasFixedSize(true);

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    Toast.makeText(getApplicationContext(),
                                                            "Error trying to get EMPTY", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("CANCELLED", "Error trying to get products ");
                            Toast.makeText(getApplicationContext(),
                                    "Error trying to get products", Toast.LENGTH_SHORT).show();
                        }
                    });

                Query queryM = product.orderByChild("Manufacturer").startAt(manuToSearch).endAt(manuToSearch+ "/uf8ff");
                queryM.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                Product product = new Product();
                                createProduct(child,product);
                                if (productToSearch.equals("")) {
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
                        Log.d("CANCELLED", "Error trying to get manufacturers ");
                        Toast.makeText(getApplicationContext(),
                                "Error trying to get manufacturers", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        //Child the root before all the push() keys are found and add a ValueEventListener()
        database.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                noDuplicatesP.clear();
                noDuplicatesM.clear();
                autoCompleteProduct.clear();
                autoCompleteProduct.clear();
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
        buildAlertDialog(p.get(productPosition));
        //Product product = p.get(productPosition);


    }

    public Product createProduct(DataSnapshot child, Product product){
        product.setBarcode(child.getValue(Product.class).getBarcode());
        product.setProductName(child.getValue(Product.class).getProductName());
        product.setCategoryName(child.getValue(Product.class).getCategoryName());
        product.setSubCategoryName(child.getValue(Product.class).getSubCategoryName());
        product.setManufacturer(child.getValue(Product.class).getManufacturer());
        product.setImporter(child.getValue(Product.class).getImporter());
        product.setIsGlutenFree(child.getValue(Product.class).getIsGlutenFree());
        product.setAdditionalInfo(child.getValue(Product.class).getAdditionalInfo());
        product.setDateValid(child.getValue(Product.class).getDateValid());
        product.setWeight(child.getValue(Product.class).getWeight());
        product.setPassover(child.getValue(Product.class).isPassover());
        return product;
    }

    public void buildAlertDialog(Product product) {
        //building an alert dialog
        AlertDialog.Builder Results = new AlertDialog.Builder(this);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.cancel();
         String Title="";
         String FirstLingMessage = "";
         String SecondLingMessage = "";
         String ThirdLingMessage = "";

        //setting the content of the alert dialog
        if (product.getBarcode().equals("Not Found")) {
            Title = "המוצר לא נמצא!";
            FirstLingMessage = "המוצר שביקשת אינו נמצא במאגר, ועל כן אין מידע נוסף לגביו.";
            SecondLingMessage = "במידה ותרצה תוכל לפנות לעמותה והנושא יבדק.";
        } else {
            FirstLingMessage = "שם המוצר: " + product.getProductName();
            SecondLingMessage = "יצרן: " + product.getManufacturer();
            if (product.getIsGlutenFree().equals("N")) {
                ThirdLingMessage = "";
            } else ThirdLingMessage = "תאריך אישור: " + product.getDateValid();
        }

        //setting the title of the alert dialog
        if (product.getIsGlutenFree().equals("Y")) {
            Title = "המוצר אינו מכיל גלוטן";
            Results.setIcon(R.drawable.nogluteninside);
        } else if (product.getIsGlutenFree().equals("N")) {
            Title = "המוצר מכיל גלוטן";
            Results.setIcon(R.drawable.hasgluten);
        } else if (product.getIsGlutenFree().equals("M")) {
            Title = "המוצר עלול להכיל גלוטן";
            Results.setIcon(R.drawable.mayhavegluten);
        }

        //poping the specific alert according to the barcode
        Results.setTitle(Title);

        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.alertdialog, null);
        //ImageView image= (ImageView) view.findViewById(R.id.imageAlert);
       // StorageReference spaceRef = storageRef.child(IMGref);
       // Glide.with(getApplicationContext()).load(spaceRef).into(image);
        TextView text= (TextView) view.findViewById(R.id.messageAlert);
        text.setText( "\n"+FirstLingMessage + "\n" + "\n"+ SecondLingMessage + "\n" + "\n"+ ThirdLingMessage);
        Results.setCancelable(false);
        Results.setView(view);

        //defining the positive button that will let the user scan again
        Results.setPositiveButton(
                "חזרה לרשימת התוצאות",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog showInfo = Results.create();

        showInfo.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.WHITE);
                showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(17);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                }
            }
        });

        showInfo.show();
    }//buildAlertDialog

}
