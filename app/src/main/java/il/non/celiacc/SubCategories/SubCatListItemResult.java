package il.non.celiacc.SubCategories;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import il.non.celiacc.MySqliteOpenHelper;
import il.non.celiacc.Products.Product;
import il.non.celiacc.R;

public class SubCatListItemResult extends AppCompatActivity
        implements SubCatListAdapter.SubCatListAdapterOnClickHandler{

   ArrayList<Product> c = new ArrayList<>();
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
                        product.setProductName(child.getValue(Product.class).getProductName());
                        product.setBarcode(child.getValue(Product.class).getBarcode());
                        product.setCategoryName(child.getValue(Product.class).getCategoryName());
                        c.add(product);
                    }

                    adapt.setSubListData(c);
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




            // get the text to pass
            String catNameToPass = c.get(productPosition).getCategoryName();
            // start the SecondActivity
            Intent intent = new Intent(SubCatListItemResult.this, SubCategoryGridActivity.class);
            intent.putExtra("CATEGORY_NAME", catNameToPass);
            startActivity(intent);

        }
    }

