package il.non.celiacc.SubCategories;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import il.non.celiacc.MainMenu;
import il.non.celiacc.MySqliteOpenHelper;
import il.non.celiacc.Products.Product;
import il.non.celiacc.Products.SearchProductActivity;
import il.non.celiacc.R;

public class SubCatListItemResult extends AppCompatActivity
        implements SubCatListAdapter.SubCatListAdapterOnClickHandler{

   ArrayList<Product> p = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_sub_cat_result);
        // get the text from MainActivity
        Intent intent2 = getIntent();
        final String Subtext = intent2.getStringExtra("SUB_CATEGORY");
        final String Cattext = intent2.getStringExtra("CATEGORY");

        // use the text
        final RecyclerView recyclerView = findViewById(R.id.rvListOfProductsSubCatResult);
       final SubCatListAdapter adapt = new SubCatListAdapter(getApplicationContext(), (SubCatListAdapter.SubCatListAdapterOnClickHandler) SubCatListItemResult.this);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference subcategory = reference.child("Products");
        Query query = subcategory.orderByChild("SubCategoryName").equalTo(Subtext);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Product product = new Product();
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
                        product.setWeight(child.getValue(Product.class).getWeight());
                        product.setPassover(child.getValue(Product.class).isPassover());
                        p.add(product);
                    }

                    adapt.setSubListData(p);
                    recyclerView.setAdapter(adapt);
                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(
                            getApplicationContext());
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setHasFixedSize(true);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("CANCELLED", "Error trying to get subcategories ");
                Toast.makeText(getApplicationContext(),
                        "Error trying to get subcategories", Toast.LENGTH_SHORT).show();
            }
        });

        Button button = findViewById(R.id.btBackToSubCat);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intentExit = new Intent(SubCatListItemResult.this, SubCategoryGridActivity.class);
                intentExit.putExtra("CATEGORY_NAME", Cattext);
                startActivity(intentExit);
            }
        });

    }
        @Override
        public void onClick ( int productPosition){

        //barcode code
            buildAlertDialog(p.get(productPosition));

        }
    public void buildAlertDialog(final Product product) {
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
        TextView text= (TextView) view.findViewById(R.id.messageAlert);
        text.setText( "\n"+FirstLingMessage + "\n" + "\n"+ SecondLingMessage + "\n" + "\n"+ ThirdLingMessage);
        Results.setCancelable(false);
        Results.setView(view);

        //defining the positive button that will let the user scan again
        Results.setPositiveButton(
                "אישור",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        Results.setNeutralButton(
                "חזרה לתת הקטגוריה",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                                    // get the text to pass
            String catNameToPass = product.getCategoryName();
            // start the SecondActivity
            Intent intent = new Intent(SubCatListItemResult.this, SubCategoryGridActivity.class);
            intent.putExtra("CATEGORY_NAME", catNameToPass);
            startActivity(intent);
                        ;
                    }
                });

        // change color of the buttons in alert dialo
        final AlertDialog showInfo = Results.create();

        showInfo.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                showInfo.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.BLACK);
                showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(15);
                showInfo.getButton(AlertDialog.BUTTON_NEUTRAL).setTextSize(15);
                if (product.getIsGlutenFree().equals("Y")){
                    showInfo.getButton(AlertDialog.BUTTON_NEUTRAL).setBackgroundColor(Color.parseColor("#3eb959"));
                    showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.parseColor("#3eb959"));
                }
                else if (product.getIsGlutenFree().equals("N"))
                {
                    showInfo.getButton(AlertDialog.BUTTON_NEUTRAL).setBackgroundColor(Color.parseColor("#ff3943"));
                    showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.parseColor("#ff3943"));
                }
                else{
                    showInfo.getButton(AlertDialog.BUTTON_NEUTRAL).setBackgroundColor(Color.parseColor("#FAEBD7"));
                    showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.parseColor("#FAEBD7"));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    showInfo.getButton(AlertDialog.BUTTON_NEUTRAL).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
            }
        });


        showInfo.show();
    }//buildAlertDialog


}

