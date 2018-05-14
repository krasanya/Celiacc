package il.non.celiacc.Categories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import il.non.celiacc.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryAdapterViewHolder> {
    Bitmap  bitmap;
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
        Picasso.get().load(mCategoryList.get(position).getIMG()).into(holder.image);
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
//    public class GetImageFromURL extends AsyncTask<String,Void,Bitmap> {
//        ImageView imgV;
//
//        public GetImageFromURL(ImageView imgV) {
//            this.imgV = imgV;
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... url) {
//            String urlDisplay = url[0];
//            bitmap = null;
//            try {
//                InputStream str = new java.net.URL(urlDisplay).openStream();
//                BitmapFactory.decodeStream(str);} catch (Exception e){
//                e.printStackTrace();
//            }
//
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
//            imgV.setImageBitmap(bitmap);
//        }
//    }


