package il.non.celiacc;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarcodeScan extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;
    public String resultCamera;

    private DatabaseReference databaseReferenceProducts ;
    private DatabaseReference databaseReferenceBarcode;
    private DatabaseReference databaseReferenceProductName;
    private DatabaseReference databaseReferenceIsGlutenFree ;
    private DatabaseReference databaseReferenceWeight ;
    private DatabaseReference databaseReferenceManufacturer;
    private DatabaseReference databaseReferenceDateValid;
    private DatabaseReference databaseReferenceProductIMG;

    private StorageReference storageRef;
    private String IMGref;

    private Boolean NotFound;
    private String parent;
    private String currentBarcode;
    private String IsGlutenFree;
    private String ProductName;
    private String Weight;
    private String IMGURL;
    private String Manufacturer;
    private String DateValid;

    private String Title="";
    private String FirstLingMessage = "";
    private String SecondLingMessage = "";
    private String ThirdLingMessage = "";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requesting for permission for the camera if there isnt
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    0);
        }
        progressDialog = new ProgressDialog(this);
        //defining the scanner and starting the camera
        scannerView = new ZXingScannerView(this);
        //scannerView.setResultHandler(new ZXingScannerResultHandler());
        setContentView(scannerView);
        scannerView.startCamera();

        databaseReferenceProducts = FirebaseDatabase.getInstance().getReference().child("Products");
        storageRef=FirebaseStorage.getInstance().getReference();




    }//OnCreate

    @Override
    public  void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);    // Register ourselves as a handler for scan results.
        scannerView.startCamera();             // Start camera on resume
    }//OnResume


    //stopping the camera when not in use
    public void OnPause() {
        super.onPause();
        scannerView.stopCamera();
    }//OnPause

    //@Override
    public void handleResult(Result result) {
        resultCamera = (String)result.getText();

        if (resultCamera.substring(1,1).equals("0")){
            resultCamera=resultCamera.substring(2);
        }

        findBarcode(resultCamera);

        setContentView(R.layout.activity_barcode_scan);
            scannerView.stopCamera();
    }//handleResult

    public void findBarcode(final String barcode){
        final String barcodeCode= barcode;
        final List<String> grandChildren= new ArrayList<>();
        NotFound=true;
        IsGlutenFree="Not Found";
        progressDialog.setMessage("נא להמתין");
        progressDialog.show();
        databaseReferenceProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //all the children of the table users
                Iterable <DataSnapshot> children= dataSnapshot.getChildren();
                //ניגש לכל ילד שתחת הטלבה של היוזרס
                for (DataSnapshot child : children){
                    if (NotFound==true){
                        //שומר את האבא של הערכים הרלוונטים ליוזר הספציפי- הקוד שלו בפיירבייס
                        parent = child.getKey();
                        //כל הערכים שתחת היוזר הספציפי
                        Iterable<DataSnapshot> grandchild= child.getChildren();
                        for (DataSnapshot Gchild: grandchild){

                            currentBarcode = Gchild.getValue().toString();
                            if (currentBarcode.equals(barcodeCode)) {
                                NotFound = false;
                                IMGref="products/"+barcodeCode+".jpg";

                                //retrieving the data of the product IG GLUTEN FREE
                                databaseReferenceIsGlutenFree = databaseReferenceProducts.child(parent).child("IsGlutenFree");
                                databaseReferenceIsGlutenFree.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        IsGlutenFree = dataSnapshot.getValue().toString();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                databaseReferenceDateValid = databaseReferenceProducts.child(parent).child("DateValid");
                                databaseReferenceManufacturer = databaseReferenceProducts.child(parent).child("Manufacturer");
                                databaseReferenceManufacturer.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Manufacturer = dataSnapshot.getValue().toString();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                databaseReferenceWeight = databaseReferenceProducts.child(parent).child("Weight");
                                databaseReferenceBarcode = databaseReferenceProducts.child(parent).child("Barcode");
                                databaseReferenceProductName = databaseReferenceProducts.child(parent).child("ProductName");
                                databaseReferenceProductIMG = databaseReferenceProducts.child(parent).child("IMG");
                                databaseReferenceProductName.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        ProductName = dataSnapshot.getValue().toString();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                databaseReferenceProductIMG.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        IMGURL = dataSnapshot.getValue().toString();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                databaseReferenceDateValid.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        DateValid = dataSnapshot.getValue().toString();
                                        buildAlertDialog(IsGlutenFree, Manufacturer, DateValid, ProductName,IMGURL);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                    break;

                            }
                        }
                    } else break;
                }
            }//OnDataChange

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }//findBarcode



    public void buildAlertDialog(String glutenFree, String ProductManufacturer, String ProductDateValid, String Name, String IMG) {
        //building an alert dialog
        AlertDialog.Builder Results = new AlertDialog.Builder(this);
        progressDialog.cancel();

        //setting the content of the alert dialog
        if (glutenFree.equals("Not Found")) {
            Title = "המוצר לא נמצא!";
            FirstLingMessage = "המוצר שביקשת אינו נמצא במאגר, ועל כן אין מידע נוסף לגביו.";
            SecondLingMessage = "במידה ותרצה תוכל לפנות לעמותה והנושא יבדק.";
        } else {
            FirstLingMessage = "שם המוצר: " + Name;
            SecondLingMessage = "יצרן: " + ProductManufacturer;
            if (glutenFree.equals("N")) {
                ThirdLingMessage = "";
            } else ThirdLingMessage = "תאריך אישור: " + ProductDateValid;
        }

        //setting the title of the alert dialog
        if (glutenFree.equals("Y")) {
            Title = "המוצר אינו מכיל גלוטן";
            Results.setIcon(R.drawable.nogluteninside);
        } else if (glutenFree.equals("N")) {
            Title = "המוצר מכיל גלוטן";
            Results.setIcon(R.drawable.hasgluten);
        } else if (glutenFree.equals("M")) {
            Title = "המוצר עלול להכיל גלוטן";
            Results.setIcon(R.drawable.mayhavegluten);
        }


        //poping the specific alert according to the barcode
        Results.setTitle(Title);

        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.alertdialog, null);
        ImageView image= (ImageView) view.findViewById(R.id.imageAlert);
        StorageReference spaceRef = storageRef.child(IMGref);
        Glide.with(getBaseContext()).load(spaceRef).into(image);
        TextView text= (TextView) view.findViewById(R.id.messageAlert);
        text.setText( "\n"+FirstLingMessage + "\n" + "\n"+ SecondLingMessage + "\n" + "\n"+ ThirdLingMessage);
        Results.setCancelable(false);
        Results.setView(view);



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
                        dialog.cancel();
                        ;
                    }
                });

        // change color of the buttons in alert dialo
        final AlertDialog showInfo = Results.create();

        showInfo.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.WHITE);
                showInfo.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                showInfo.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.WHITE);
                showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(17);
                showInfo.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(17);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    showInfo.getButton(AlertDialog.BUTTON_POSITIVE).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    showInfo.getButton(AlertDialog.BUTTON_NEGATIVE).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
            }
        });

//        //setting the colour of the alert dialog
//        if (glutenFree.equals("Y")) {
//            showInfo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            showInfo.getWindow().setBackgroundDrawableResource(R.color._GreenLight);
//        } else if (glutenFree.equals("N")) {
//            showInfo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            showInfo.getWindow().setBackgroundDrawableResource(R.color._redLight);
//        } else if (glutenFree.equals("M")) {
//            showInfo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            showInfo.getWindow().setBackgroundDrawableResource(R.color._yellow);
//        }
        showInfo.show();
    }//buildAlertDialog


}



