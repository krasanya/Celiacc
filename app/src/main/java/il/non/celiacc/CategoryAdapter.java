package il.non.celiacc;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.widget.ArrayAdapter;



public class CategoryAdapter extends ArrayAdapter<HashMap<String, String>> {

    public ArrayList<HashMap<String, String>> myList;
    Activity activity;
    MySqliteOpenHelper db = new MySqliteOpenHelper(getContext());

    public CategoryAdapter(Activity activity, ArrayList<HashMap<String, String>> myList){
        super(activity, R.layout.cat__grid, myList);
        this.activity = activity;
        this.myList = myList;
    }

    @Override
    public int getCount(){
        return myList.size();
    }

    @Override
    public HashMap<String,String> getItem(int position){
        return myList.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    public View getView(int position ,View convertView, ViewGroup parent) {

        final HashMap<String, String> map = myList.get(position);
        final ItemViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ItemViewHolder();
            convertView = activity.getLayoutInflater().inflate(R.layout.cat__grid, null, true);
            convertView.setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT);
            viewHolder.cat_name = (TextView) convertView.findViewById(R.id.tvCat);
            viewHolder.cat_image = (TextView) convertView.findViewById(R.id.iv_cat_image);
            viewHolder.exit = (Button) convertView.findViewById(R.id.btBackToMain);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder) convertView.getTag();
        }

        viewHolder.cat_name.setText(map.get("CategoryName"));
        viewHolder.cat_image.setText(map.get("Tag1"));

        // ON CLICK _ SHOW INFORMATION
        viewHolder.cat_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get the text to pass
                   String textToPass = viewHolder.cat_name.getText().toString();

                // start the SecondActivity
                Intent intent = new Intent(getContext(), subcat_Grid.class);
                intent.putExtra(Intent.EXTRA_TEXT, textToPass);
                activity.startActivity(intent);

            }
        });

        //ON EXIT
        viewHolder.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentExit = new Intent(getContext(),MainMenu.class);
                activity.startActivity(intentExit);

            }
        });
        return convertView;
    }

    public class ItemViewHolder {
        TextView cat_name;
        TextView cat_image;
        Button exit;
    }
}


//public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MainViewHolder> {
//    LayoutInflater inflater;
//    HashMap<String,String> modelList;
//    //public ArrayList<HashMap<String, String>> modelList;
//
//    public CategoryAdapter(Context context, HashMap<String, String> modelList) {
//        this.inflater = LayoutInflater.from(context);
//        this.modelList = modelList;
//    }
//
//    @Override
//    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = inflater.inflate(R.layout.category_item, parent, false);
//        return new MainViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(MainViewHolder holder, int position) {
//        //final HashMap<String, String> map = modelList.get(position);
//        //holder.bindData();
//        //holder.mainText.setText(map.get("CategoryName"));
//        //holder.subText.setText(map.get("Tag1"));
//        holder.mainText.setText(modelList.get("CategoryName"));
//       //Log.d("1111111111",holder.mainText.getText(modelList.get("CategoryName")).toString()) ;
//        holder.subText.setText(modelList.get("Tag1"));
//       // Log.d("2222222222222",holder.subText.getText()) ;
//
//       // holder.cardView.
//    }
//
//    @Override
//    public int getItemCount() {
//        return modelList.size();
//    }
//
//   //public HashMap<String,String> getItem(int position){
//   //     return modelList.get(position);
//   // }
//
//    class MainViewHolder extends RecyclerView.ViewHolder {
//        TextView mainText, subText;
//        public MainViewHolder(View itemView) {
//            super(itemView);
//            mainText = (TextView) itemView.findViewById(R.id.tvCat);
//            subText = (TextView) itemView.findViewById(R.id.iv_cat_image);
//
//        }
//
//    }
//}
