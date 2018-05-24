package il.non.celiacc.SubCategories;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import il.non.celiacc.Categories.CategoryGridActivity;
import il.non.celiacc.MainMenu;
import il.non.celiacc.R;


public class SubCategoryGridActivity extends AppCompatActivity
implements SubCategoryAdapter.SubCategoryAdapterOnClickHandler{

    private ProgressDialog progressDialog;
    ArrayList<SubCategory> c = new ArrayList<>();
        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_category_grid_activity);
            progressDialog = new ProgressDialog(this); // on create
            progressDialog.setMessage("נא להמתין");
            progressDialog.show();

        // get the text from Category
        Intent intent = getIntent();
        final String textCategory = intent.getStringExtra("CATEGORY_NAME");
        // use the text

            final RecyclerView recyclerView = findViewById(R.id.rvSubCategory);
            final SubCategoryAdapter subcategoryAdapter = new SubCategoryAdapter(getApplicationContext(),  (SubCategoryAdapter.SubCategoryAdapterOnClickHandler)SubCategoryGridActivity.this);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference subcategory = reference.child("SubCategories");
            Query query = subcategory.orderByChild("category").equalTo(textCategory);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            SubCategory cat = new SubCategory();
                            cat.setSubCategoryName(child.getValue(SubCategory.class).getSubCategoryName());
                            cat.setCategory(child.getValue(SubCategory.class).getCategory());
                            cat.setImg(child.getValue(SubCategory.class).getImg());
                            c.add(cat);
                        }
                        subcategoryAdapter.setSubCategoryData(c);
                        recyclerView.setAdapter(subcategoryAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                                getApplicationContext(), 3, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.setHasFixedSize(true);
                    }
                    progressDialog.cancel();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("CANCELLED", "Error trying to get subcategories ");
                    Toast.makeText(getApplicationContext(),
                            "Error trying to get subcategories",Toast.LENGTH_SHORT).show();
                }
            });
        Button buttonMain = findViewById(R.id.btBackTomain);
            buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intentExit = new Intent(SubCategoryGridActivity.this, MainMenu.class);
                startActivity(intentExit);
            }
        });
            Button button = findViewById(R.id.catFromSub);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent intentExit = new Intent(SubCategoryGridActivity.this, CategoryGridActivity.class);
                    startActivity(intentExit);
                }
            });

    }

        @Override
        public void onClick ( int categoryPosition){
        // get the text to pass
        String subCatNameToPass = c.get(categoryPosition).getSubCategoryName();
            String textCategory = c.get(categoryPosition).getCategory();
        // start the SecondActivity
         finish();
        Intent intent = new Intent(SubCategoryGridActivity.this, SubCatListItemResult.class);
        intent.putExtra("SUB_CATEGORY", subCatNameToPass);
            intent.putExtra("CATEGORY", textCategory);
        startActivity(intent);
    }
    }
