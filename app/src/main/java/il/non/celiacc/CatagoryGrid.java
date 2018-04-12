package il.non.celiacc;

        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.widget.GridView;
        import android.widget.ImageView;
        import android.widget.Toast;
        import java.util.ArrayList;


public class CatagoryGrid extends AppCompatActivity {

    GridView gridView;
    ArrayList<Category> list;
    CatagoryAdapter adapter = null;
    MySqliteOpenHelper sqlHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory_grid);

        gridView = (GridView) findViewById(R.id.gridViewcat);
        list = new ArrayList<>();
        adapter = new CatagoryAdapter(this, R.layout.catagory_item, list);
        gridView.setAdapter(adapter);

        // get all data from sqlite
       // Cursor cursor = sqlHelper.getData("SELECT CategoryName,IMG FROM CATEGORIES");
       // list.clear();
       // while (cursor.moveToNext()) {
       //     String name = cursor.getString(0);
       //     byte[] image = cursor.getBlob(1);

       //     list.add(new Category(name, image));
       // }

    }

    ImageView imageViewFood;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
