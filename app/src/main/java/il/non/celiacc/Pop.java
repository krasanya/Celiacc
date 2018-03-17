package il.non.celiacc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;


/**
 * Created by admin on 17/03/2018.
 */

public class Pop extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);

        //adjusting the pop up height and width according to the phone's hight and width

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.5));
    }
    //defining the scanner and starting the camera
    public void BackToScanner(View code){
        Intent BarcodeIntent = new Intent (Pop.this,BarcodeScan.class);
        startActivity(BarcodeIntent);
    }

    public void ClosePop (View code){
        finish();
    }
    public void BackToMenu(View code){
        Intent MenuIntent = new Intent (Pop.this,MainMenu.class);
        startActivity(MenuIntent);
    }

}
