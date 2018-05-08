package il.non.celiacc.SubCategories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import il.non.celiacc.Categories.CategoryAdapter;
import il.non.celiacc.R;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryAdapterViewHolder> {

    private ArrayList<SubCategory> mSubCategoryList ;
    private SubCategoryAdapter.SubCategoryAdapterOnClickHandler mOnClickHandler;
    private Context mContext;

    public SubCategoryAdapter(Context context, SubCategoryAdapter.SubCategoryAdapterOnClickHandler onClickHandler){
        mContext = context;
        mOnClickHandler = onClickHandler;
    }

    public interface SubCategoryAdapterOnClickHandler{
        void onClick(int categoryPosition);
    }

    @NonNull
    @Override
    public SubCategoryAdapter.SubCategoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.list_item_category;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutId, parent, false);
        return new SubCategoryAdapter.SubCategoryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.SubCategoryAdapterViewHolder holder, int position) {
        if(mSubCategoryList.get(position) != null) {
            holder.name.setText(mSubCategoryList.get(position).getSubCategoryName());
        }
    }

    @Override
    public int getItemCount() {
        if(mSubCategoryList == null){
            return 0;
        }
        return mSubCategoryList.size();
    }

    public void setSubCategoryData(ArrayList<SubCategory> subCategoryList){
       mSubCategoryList =  subCategoryList;
        notifyDataSetChanged();
    }

    public class SubCategoryAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        TextView name;
        ImageView image;

        public SubCategoryAdapterViewHolder(View itemView) {
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