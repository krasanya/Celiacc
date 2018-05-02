package il.non.celiacc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarcodeScan extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;
    public String resultCamera;

    //db
    MySqliteOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MySqliteOpenHelper(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    0);
        }
        //defining the scanner and starting the camera
        scannerView = new ZXingScannerView(this);
        //scannerView.setResultHandler(new ZXingScannerResultHandler());
        setContentView(scannerView);
        scannerView.startCamera();

    }

    @Override
    public  void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);    // Register ourselves as a handler for scan results.
        scannerView.startCamera();             // Start camera on resume
    }


    //stopping the camera when not in use
    public void OnPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    //@Override
    public void handleResult(Result result) {
        resultCamera = (String)result.getText();
        if (resultCamera.substring(1,1).equals("0")){
            resultCamera=resultCamera.substring(2);
        }
        String Title="";
        String FirstLingMessage = "";
        String SecondLingMessage = "";
        String ThirdLingMessage = "";
        String BarcodeC="";


        //getting the IsGlutenFree value from the specific barcode
        Cursor c = db.findProductBarcodeCursor(resultCamera);

        if (c==null)
        {
            BarcodeC="Not Found";
            Toast.makeText(getApplicationContext(),BarcodeC,Toast.LENGTH_LONG).show();
        }
        else{
            c.moveToFirst();
            BarcodeC= c.getString(c.getColumnIndex("IsGlutenFree"));
            Toast.makeText(getApplicationContext(),BarcodeC,Toast.LENGTH_LONG).show();
        }

        //building an alert dialog
        AlertDialog.Builder Results = new AlertDialog.Builder(this);

        //setting the content of the alert dialog
        if (BarcodeC.equals("Not Found")){
                Title=resultCamera;
                FirstLingMessage="המוצר שביקשת אינו נמצא במאגר, ועל כן אין מידע נוסף לגביו";
                SecondLingMessage="במידה ותרצה תוכל לפנות לעמותה והנושא יבדק.";
            }
            else
            {
                FirstLingMessage="שם המוצר: "+c.getString(c.getColumnIndex("ProductName"));
                SecondLingMessage="יצרן: "+c.getString(c.getColumnIndex("Manufacturer"));
                if (BarcodeC.equals("N")){
                    ThirdLingMessage="";
                }
                else ThirdLingMessage="תאריך אישור: "+c.getString(c.getColumnIndex("dateValid"));
            }

        //setting the title of the alert dialog
        if (BarcodeC.equals("Y")) {
            Title = "המוצר אינו מכיל גלוטן";

            }
        else if (BarcodeC.equals("N")) {
            Title = "המוצר מכיל גלוטן";
        }
        else if (BarcodeC.equals("M")) {
            Title="המוצר עלול להכיל גלוטן";
        }

        //poping the specific alert according to the barcode

            Results.setTitle(Title);
            Results.setMessage(FirstLingMessage + "\n" + SecondLingMessage + "\n" + ThirdLingMessage);
            Results.setCancelable(false);

            //defining the positive button that will let the user scan again
            Results.setPositiveButton(
                    "סרוק מוצר נוסף",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent RegIntent = new Intent(BarcodeScan.this, BarcodeScan.class);
                          //  RegIntent.putExtra(Intent.EXTRA_TEXT, textUsername); //PASS USER
                            startActivity(RegIntent);
                        }
                    });
            //defining the negative button that will return the user to the main menu
            Results.setNegativeButton(
                    "חזרה לתפריט ראשי",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent RegIntent = new Intent(BarcodeScan.this, MainMenu.class);

                            startActivity(RegIntent);
                            dialog.cancel();;
                        }
                    });

        // change color of the buttons in alert dialog
        final AlertDialog showInfo = Results.create();

        showInfo.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                showInfo.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });

        //setting the colour of the alert dialog
        if (BarcodeC.equals("Y")) {
            showInfo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            showInfo.getWindow().setBackgroundDrawableResource(R.color._GreenLight);
        }
        else if (BarcodeC.equals("N")) {
            showInfo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            showInfo.getWindow().setBackgroundDrawableResource(R.color._red);
        }
        else if (BarcodeC.equals("M")) {
            showInfo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            showInfo.getWindow().setBackgroundDrawableResource(R.color._yellow);
        }
            showInfo.show();

            setContentView(R.layout.activity_barcode_scan);
            scannerView.stopCamera();
    }




    //class ZXingScannerResultHandler implements ZXingScannerView.ResultHandler {

        //what we do with the result is in this method

        //@Override
        //public void handleResult(Result result) {
        //    resultCamera = result.getText();

         //   String BarcodeFound= db.findProductBarcode(resultCamera);

         //   switch (BarcodeFound) {
         //       case "N":
         //           startActivity(new Intent(BarcodeScan.this, PopNoFreeGluten.class));
        //            break;
         //       case "Y":
         //           startActivity(new Intent(BarcodeScan.this, PopYesFreeGluten.class));
        //            break;
        //        case "M":
        //            startActivity(new Intent(BarcodeScan.this, PopMaybeFreeGluten.class));
        //            break;
         //       default:
         //           startActivity(new Intent(BarcodeScan.this, PopNoFreeGluten.class));
        //    }
            //Toast.makeText(BarcodeScan.this, resultCamera, Toast.LENGTH_LONG).show();

         //   setContentView(R.layout.activity_barcode_scan);
        //    scannerView.stopCamera();
      //  }
    //}
}


