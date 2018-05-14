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
        String Subtext = intent2.getStringExtra("SUB_CATEGORY");

        // use the text
        final RecyclerView recyclerView = findViewById(R.id.rvListOfProductsSubCatResult);
       final SubCatListAdapter adapt = new SubCatListAdapter(getApplicationContext(), (SubCatListAdapter.SubCatListAdapterOnClickHandler) SubCatListItemResult.this);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference subcategory = reference.child("Products");
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
        Results.setNegativeButton(
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
                showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.WHITE);
                showInfo.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                showInfo.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.WHITE);
                showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(17);
                showInfo.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(17);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    showInfo.getButton(AlertDialog.BUTTON_NEGATIVE).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
            }
        });


        showInfo.show();
    }//buildAlertDialog


}

