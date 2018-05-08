package il.non.celiacc.Categories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import il.non.celiacc.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryAdapterViewHolder> {

    private CategoryAdapterOnClickHandler mOnClickHandler;
    private Context mContext;
    private ArrayList<Category> mCategoryList ;

    public CategoryAdapter(Context context, CategoryAdapterOnClickHandler onClickHandler){
        mContext = context;
        mOnClickHandler = onClickHandler;
    }

    public interface CategoryAdapterOnClickHandler{
        void onClick(int categoryPosition);
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.list_item_category;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutId, parent, false);
        return new CategoryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapterViewHolder holder, int position) {
        if(mCategoryList.get(position) != null) {
        holder.name.setText(mCategoryList.get(position).getCategoryName());

        }
    }

    @Override
    public int getItemCount() {
        if(mCategoryList == null){
            return 0;
        }
        return mCategoryList.size();
    }

    public void setCategoryData( ArrayList<Category> category){
        mCategoryList = category;
        notifyDataSetChanged();
    }


    public class CategoryAdapterViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{

        TextView name;
        ImageView image;

        public CategoryAdapterViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvcatName);
            image =  itemView.findViewById(R.id.ivCatImage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickHandler.onClick(position);
        }
    }


}
