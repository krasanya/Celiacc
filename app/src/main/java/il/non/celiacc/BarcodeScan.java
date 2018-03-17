package il.non.celiacc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarcodeScan extends AppCompatActivity {
    private ZXingScannerView scannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scan);
    }

    //defining the scanner and starting the camera
    public void ScanCode(View code){

        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(new ZXingScannerResultHandler());
        setContentView(scannerView);
        scannerView.startCamera();
    }

    //stopping the camera when not in use
    public void OnPause(){
        super.onPause();
        scannerView.stopCamera();
    }

    class ZXingScannerResultHandler implements ZXingScannerView.ResultHandler{

        //what we do with the result is in this method

        @Override
        public void handleResult(Result result) {
            String resultCamera = result.getText();
            Toast.makeText(BarcodeScan.this,resultCamera, Toast.LENGTH_LONG).show();

            setContentView(R.layout.activity_barcode_scan);
            scannerView.stopCamera();
        }
    }
}
