package il.non.celiacc.Categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

import il.non.celiacc.MainMenu;
import il.non.celiacc.R;

public class CategoryFragment extends Fragment {

    //    MySqliteOpenHelper db = new MySqliteOpenHelper(getActivity());
    //ArrayList<HashMap<String, String>> allCats;
    HashMap<String, String> allCats;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_categories, container, false);

//        allCats = db.getAllCategories();
        RecyclerView recyclerView = view.findViewById(R.id.rvCategory);
        CategoryAdapter categoryAdapter = new CategoryAdapter(
                getContext(), (CategoryAdapter.CategoryAdapterOnClickHandler) getActivity());
        // categoryAdapter.setCategoryData(allCats);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getContext(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setHasFixedSize(true);

        Button button = view.findViewById(R.id.btBackToMain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentExit = new Intent(getContext(), MainMenu.class);
                startActivity(intentExit);
            }
        });

        return view;
    }

//    public void setData(ArrayList<HashMap<String, String>> allCats){
//        this.allCats = allCats;
//    }
//}
//    public void setData(ArrayList<Category> allCats){
//        this.allCats = allCats;
//    }
}
