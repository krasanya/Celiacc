package il.non.celiacc.SubCategories;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import il.non.celiacc.MySqliteOpenHelper;
import il.non.celiacc.Products.Product;
import il.non.celiacc.R;

public class SubCatListAdapter extends RecyclerView.Adapter<SubCatListAdapter.SubCatListAdapterViewHolder> {//extends ArrayAdapter<HashMap<String, String>> {

    private ArrayList<Product> mList ;
    private SubCatListAdapter.SubCatListAdapterOnClickHandler mOnClickHandler;
    private Context mContext;

    public SubCatListAdapter(Context context, SubCatListAdapter.SubCatListAdapterOnClickHandler onClickHandler){
        mContext = context;
        mOnClickHandler = onClickHandler;
    }

    public interface SubCatListAdapterOnClickHandler{
        void onClick(int productPosition);
    }

    @NonNull
    @Override
    public SubCatListAdapter.SubCatListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.prod_search_result;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutId, parent, false);
        return new SubCatListAdapter.SubCatListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCatListAdapter.SubCatListAdapterViewHolder holder, int position) {
        if (mList.get(position) != null) {
            holder.name.setText(mList.get(position).getProductName());
            if (mList.get(position).getIsGlutenFree().equals("Y")) {
                holder.isFreePic.setImageResource(R.drawable.nogluteninside);
            } else if (mList.get(position).getIsGlutenFree().equals("N")) {
                holder.isFreePic.setImageResource(R.drawable.hasgluten);
            } else if (mList.get(position).getIsGlutenFree().equals("M")) {
                holder.isFreePic.setImageResource(R.drawable.mayhavegluten);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(mList == null){
            return 0;
        }
        return mList.size();
    }

    public void setSubListData(ArrayList<Product> subList){
        mList =  subList;
        notifyDataSetChanged();
    }

    public class SubCatListAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        TextView name;
        ImageView isFreePic;

        public SubCatListAdapterViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvProdName);
            isFreePic =  itemView.findViewById(R.id.ivIsFreeImage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickHandler.onClick(position);
        }
    }

}
