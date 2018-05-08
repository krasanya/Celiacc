package il.non.celiacc.Users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import il.non.celiacc.MainActivity;
import il.non.celiacc.MainMenu;
import il.non.celiacc.R;


public class UpdateUser extends AppCompatActivity {

    private EditText etUsername;
    private EditText etFirstname ;
    private EditText etLastname;
    private EditText etPass ;
    private EditText etPass2 ;
    private EditText etPhone ;
    private TextView tvUsername;

    private Boolean NotFound;
    private String currentemail;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference ;
    private DatabaseReference databaseReferenceUsers ;
    private DatabaseReference databaseReferenceFirst;
    private DatabaseReference databaseReferenceLast ;
    private DatabaseReference databaseReferenceUser ;
    private DatabaseReference databaseReferencePhone;
    private DatabaseReference databaseReferencePass;

    private String currentUser;
    private String parent;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);


        progressDialog = new ProgressDialog(this);
        Button btRegister = findViewById(R.id.btUpdateUser);
        Button btBack = findViewById(R.id.btBack);
        tvUsername = findViewById(R.id.tvUsername);


        //connecting to the specific user logged in to the app
        firebaseAuth= FirebaseAuth.getInstance();

        currentUser=firebaseAuth.getCurrentUser().getEmail().toString();
        tvUsername.setText(currentUser);
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference().child("users");

        loadUserInformation();

        //when the user clicks the UPDATE button the system will update his details
        btRegister.setOnClickListener(new View.OnClickListener() {
            //on click "Register"
            public void onClick(View v) {

                    //helping var for each box
                    etPass = findViewById(R.id.etPass);
                    etPass2 =  findViewById(R.id.etPassAgain);
                    etUsername =  findViewById(R.id.etUsername);
                    etFirstname =  findViewById(R.id.etFirstname);
                    etLastname =  findViewById(R.id.etLastname);
                    etPhone =  findViewById(R.id.etPhone);

                    String strPass = etPass.getText().toString();
                    String strPass2 = etPass2.getText().toString();

                    // if passwords doesnt match - type again
                    if (!strPass.equals(strPass2)) {
                        Toast.makeText(getApplicationContext(), "הסיסמאות אינן זהות אנא נסה שנית", Toast.LENGTH_LONG)
                                .show();
                        return;
                    }
                    //password is required to be at least 6 characters
                    if (strPass.length() < 6) {
                        Toast.makeText(getApplicationContext(), "יש להזין סיסמא בעלת 6 ספרות לפחות", Toast.LENGTH_LONG).show();
                        return;
                    }
                    //phone is required to be least 10 characters
                    if (etPhone.getText().toString().length() != 10) {
                        Toast.makeText(getApplicationContext(), "יש להזין טלפון תקין", Toast.LENGTH_LONG).show();
                        return;
                    }
                    // if there are empty fields - type
                    if (etUsername.getText().toString().equals("") || etFirstname.getText().toString().equals("") || etLastname.getText().toString().equals("") || strPass.equals("")) {
                        Toast.makeText(getApplicationContext(), "יש להזין את כל השדות", Toast.LENGTH_LONG).show();
                        return;
                    }
                    // if passwords ok - move on

                    //updating the fields according to the data in the editTexts
                    databaseReferenceUser = databaseReferenceUsers.child(parent).child("Username");
                    databaseReferenceFirst = databaseReferenceUsers.child(parent).child("Firstname");
                    databaseReferenceLast = databaseReferenceUsers.child(parent).child("Lastname");
                    databaseReferencePhone = databaseReferenceUsers.child(parent).child("Phone");

                    databaseReferenceUser.setValue(etUsername.getText().toString());
                    databaseReferenceFirst.setValue(etFirstname.getText().toString());
                    databaseReferenceLast.setValue(etLastname.getText().toString());
                    databaseReferencePhone.setValue(etPhone.getText().toString());
                    Toast.makeText(getApplicationContext(), "נתונייך עודכנו בהצלחה!", Toast.LENGTH_LONG).show();

                //updating the password of the user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                //TODO: אם לא מעדכנים אז זה לא ייכנס לזה. ואיך מתריעים לאדם לצאת ולהיכנס שוב אם הוא לא מצליח//
                if (user != null) {
                    user.updatePassword(strPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("כ", "Password updated");
                                Toast.makeText(getApplicationContext(), "סיסמתך עודכנה בהצלחה!", Toast.LENGTH_LONG).show();
                            } else {
                                Log.d("כ", "Error password not updated");
                                Toast.makeText(getApplicationContext(), "סיסמתך לא השתנתה", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                finish();

            }//onClick

        });

        //when the user clicks the back button he will return to the main menu
        btBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btBack){
                    finish();
//                        Intent UpdateIntent = new Intent(UpdateUser.this, MainMenu.class);
//                        startActivity(UpdateIntent);
                }
            }
        });
    }//OnCreate
    private void loadUserInformation() {

        final String userEmail = firebaseAuth.getCurrentUser().getEmail().toString();
        final List<String> grandChildren= new ArrayList<>();
        NotFound=true;
        progressDialog.setMessage("נא להמתין");
        progressDialog.show();
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
                            currentemail=Gchild.getValue().toString();
                            if (currentemail.equals(userEmail)){
                                NotFound=false;

                                //retrieving the data of the user USER NAME and displaying in the editText
                                databaseReferenceUser=databaseReferenceUsers.child(parent).child("Username");
                                databaseReferenceUser.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        ((TextView) findViewById(R.id.etUsername)).setText(dataSnapshot.getValue().toString());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                //retrieving the data of the user FIRST NAME and displaying in the editText
                                databaseReferenceFirst=databaseReferenceUsers.child(parent).child("Firstname");
                                databaseReferenceFirst.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        ((TextView) findViewById(R.id.etFirstname)).setText(dataSnapshot.getValue().toString());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                //retrieving the data of the user LAST NAME and displaying in the editText
                                databaseReferenceLast=databaseReferenceUsers.child(parent).child("Lastname");
                                databaseReferenceLast.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        ((TextView) findViewById(R.id.etLastname)).setText(dataSnapshot.getValue().toString());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                //retrieving the data of the user PHONE and displaying in the editText
                                databaseReferencePhone=databaseReferenceUsers.child(parent).child("Phone");
                                databaseReferencePhone.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        ((TextView) findViewById(R.id.etPhone)).setText(dataSnapshot.getValue().toString());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                //retrieving the data of the user PASSWORD and displaying in the editText
                                databaseReferencePass=databaseReferenceUsers.child(parent).child("Password");
                                databaseReferencePass.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        ((TextView) findViewById(R.id.etPass)).setText(dataSnapshot.getValue().toString());
                                        ((TextView) findViewById(R.id.etPassAgain)).setText(dataSnapshot.getValue().toString());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                progressDialog.cancel();
                                break;
                            }
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }//loadUserInformation

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

}
