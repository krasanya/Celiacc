package il.non.celiacc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarcodeScan extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scan);

        //defining the scanner and starting the camera
        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(new ZXingScannerResultHandler());
        setContentView(scannerView);
        scannerView.startCamera();

    }


    //stopping the camera when not in use
    public void OnPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        String resultCamera = result.getText();
        Toast.makeText(BarcodeScan.this, resultCamera, Toast.LENGTH_LONG).show();


        startActivity(new Intent(BarcodeScan.this, Pop.class));

        setContentView(R.layout.activity_barcode_scan);
        scannerView.stopCamera();
    }

    class ZXingScannerResultHandler implements ZXingScannerView.ResultHandler {

        //what we do with the result is in this method

        @Override
        public void handleResult(Result result) {
            String resultCamera = result.getText();
            Toast.makeText(BarcodeScan.this, resultCamera, Toast.LENGTH_LONG).show();


            startActivity(new Intent(BarcodeScan.this, Pop.class));

            setContentView(R.layout.activity_barcode_scan);
            scannerView.stopCamera();
        }
    }
}


