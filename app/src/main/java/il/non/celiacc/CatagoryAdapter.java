package il.non.celiacc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CatagoryAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Category> catagoryList;

    public CatagoryAdapter(Context context, int layout, ArrayList<Category> catagoryList) {
        this.context = context;
        this.layout = layout;
        this.catagoryList = catagoryList;
    }

    @Override
    public int getCount() {
        return catagoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return catagoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.catName);
            holder.imageView = (ImageView) row.findViewById(R.id.catIMG);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Category catagory = catagoryList.get(position);

        holder.txtName.setText(catagory.getCatName());

        byte[] foodImage = catagory.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
