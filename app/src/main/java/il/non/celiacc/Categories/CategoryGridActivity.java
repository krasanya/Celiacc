package il.non.celiacc.Categories;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import il.non.celiacc.MainMenu;
import il.non.celiacc.MySqliteOpenHelper;
import il.non.celiacc.R;
import il.non.celiacc.SubCategories.SubCategoryGridActivity;

public class CategoryGridActivity extends AppCompatActivity
implements CategoryAdapter.CategoryAdapterOnClickHandler {
    private ProgressDialog progressDialog;

    ArrayList<Category> c = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        progressDialog = new ProgressDialog(this); // on create
        progressDialog.setMessage("נא להמתין");
        progressDialog.show();

        final RecyclerView recyclerView = findViewById(R.id.rvCategory);
        final CategoryAdapter categoryAdapter = new CategoryAdapter(getApplicationContext(),  (CategoryAdapter.CategoryAdapterOnClickHandler)CategoryGridActivity.this);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference category = reference.child("categories");
        Query query = category.orderByChild("CategoryName");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              if (dataSnapshot.exists()) {
                  for (DataSnapshot child : dataSnapshot.getChildren()) {
                      Category cat = new Category();
                      cat.setCategoryName(child.getValue(Category.class).getCategoryName());
                      cat.setIMG(child.getValue(Category.class).getIMG());
                      c.add(cat);
                  }
                  categoryAdapter.setCategoryData(c);
                  GridLayoutManager gridLayoutManager = new GridLayoutManager(
                          getApplicationContext(), 3, LinearLayoutManager.VERTICAL, false);
                  recyclerView.setLayoutManager(gridLayoutManager);
                  recyclerView.setAdapter(categoryAdapter);
                  recyclerView.setHasFixedSize(true);
              }
              progressDialog.cancel();
           }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("CANCELLED", "Error trying to get categories ");
                Toast.makeText(getApplicationContext(),
                        "Error trying to get categories",Toast.LENGTH_SHORT).show();
            }
        });

        Button button = findViewById(R.id.btBackToMain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intentExit = new Intent(CategoryGridActivity.this, MainMenu.class);
                startActivity(intentExit);
            }
        });

    }

    @Override
    public void onClick(int categoryPosition) {
        // get the text to pass
        String catNameToPass = c.get(categoryPosition).getCategoryName();
        // start the SecondActivity
        finish();
        Intent intent = new Intent(CategoryGridActivity.this, SubCategoryGridActivity.class);
        intent.putExtra("CATEGORY_NAME", catNameToPass);
        startActivity(intent);
    }



    }
