package il.non.celiacc.Products;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import il.non.celiacc.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterViewHolder>{
    private ArrayList<Product> mProductList ;
    private Context mContext;
    private ProductAdapterOnClickHandler mOnClickHandler;
    public ProductAdapter(Context context,ProductAdapterOnClickHandler onClickHandler) {
        mContext = context;
        mOnClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.prod_search_result;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutId, parent, false);

        return new ProductAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapterViewHolder holder, int position) {
        if(mProductList.get(position) != null) {
            holder.product_name.setText(mProductList.get(position).getProductName());
            if (mProductList.get(position).getIsGlutenFree().equals("Y")) {
                holder.isFreePic.setImageResource(R.drawable.nogluteninside);
            } else if (mProductList.get(position).getIsGlutenFree().equals("N")) {
                holder.isFreePic.setImageResource(R.drawable.hasgluten);
            } else if (mProductList.get(position).getIsGlutenFree().equals("M")) {
                holder.isFreePic.setImageResource(R.drawable.mayhavegluten);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(mProductList == null){
            return 0;
        }
        return mProductList.size();
    }

    public void setProductData(ArrayList<Product> productList){
        mProductList =  productList;
        notifyDataSetChanged();
    }

    public interface ProductAdapterOnClickHandler{
        void onClick(int productPosition);
    }

    public class ProductAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView product_name;
        ImageView isFreePic;
        public ProductAdapterViewHolder(View itemView) {
            super(itemView);
             product_name = itemView.findViewById(R.id.tvProdName);
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