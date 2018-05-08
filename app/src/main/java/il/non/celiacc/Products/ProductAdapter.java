package il.non.celiacc.Products;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import il.non.celiacc.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterViewHolder>{
    //public ArrayList<HashMap<String, String>> mProductList;
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
            //holder.product_name.setText(mProductList.get(position).get("Product"));
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

        Log.d("IN ADAPTER", "Value: " + mProductList.size());
        for (int i=0 ; i< mProductList.size();i++){
            Log.d("PRODUCTS",mProductList.get(i).getProductName());
            Log.d("PRODUCTS",mProductList.get(i).getBarcode().toString());
        }
    }

    public interface ProductAdapterOnClickHandler{
        void onClick(int productPosition);
    }

    public class ProductAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView product_name;
        TextView help_barcode;
        public ProductAdapterViewHolder(View itemView) {
            super(itemView);
             product_name = itemView.findViewById(R.id.tvProdName);
             help_barcode =  itemView.findViewById(R.id.tvHelpBarcode);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickHandler.onClick(position);
        }
    }
}