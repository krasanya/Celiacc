package il.non.celiacc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import il.non.celiacc.Categories.CategoryGridActivity;
import il.non.celiacc.Products.SearchProductActivity;
import il.non.celiacc.Users.UpdateUser;

public class MainMenu extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView UserEmail;
    private DatabaseReference databaseReferenceUsers;
    private DatabaseReference databaseReferenceUserEmail;
    private boolean NotFound;
    private String parent;
    private String currentEmail;
    private String userEmail;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        firebaseAuth=FirebaseAuth.getInstance();

        //going back to the log in page if there is no user loged in
        if (firebaseAuth.getCurrentUser()==null){
            finish();
            Intent ExitIntent = new Intent (MainMenu.this,MainActivity.class);
            startActivity(ExitIntent);
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        userEmail=user.getEmail();

        NotFound=true;
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference().child("users");
        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
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

                            String currentEmail = Gchild.getValue().toString();
                            if (currentEmail.equals(userEmail)) {
                                NotFound = false;

                                //retrieving the data of the product IG GLUTEN FREE
                                databaseReferenceUserEmail = databaseReferenceUsers.child(parent).child("Username");
                                databaseReferenceUserEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        userName = dataSnapshot.getValue().toString();
                                        //setting the title of the page
                                        UserEmail= (TextView) findViewById(R.id.TitleToPage);
                                        UserEmail.setText("ברוכה הבאה "+userName);
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


        Button Barcodebutton = (Button) findViewById(R.id.buttonBarcode);
        Button Updatebutton = (Button) findViewById(R.id.buttonUpdateUser);
        Button Exitbutton = (Button) findViewById(R.id.buttonExitMain);
        Button Searchbutton = (Button) findViewById(R.id.buttonSearch);
        Button ButtonCat = (Button) findViewById(R.id.buttonSearchCat);
        Log.d("vjh","0");
        Barcodebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BarcodeIntent = new Intent (MainMenu.this,BarcodeScan.class);
                Log.d("vjh","1");
                startActivity(BarcodeIntent);
            }
        });
        Updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent UpdateIntent = new Intent (MainMenu.this,UpdateUser.class);
                startActivity(UpdateIntent);
            }
        });
        Exitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("vjh","2");

                //signing out with the user that is signed to the app
                firebaseAuth.signOut();
                finish();
                //going back to the log in page
                Intent ExitIntent = new Intent (MainMenu.this,MainActivity.class);
                startActivity(ExitIntent);
            }
        });
        Searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SearchIntent = new Intent (MainMenu.this,SearchProductActivity.class);
                startActivity(SearchIntent);
            }
        });

        ButtonCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SearchIntent = new Intent (MainMenu.this,CategoryGridActivity.class);
                startActivity(SearchIntent);
            }
        });
    }
}

