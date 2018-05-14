package il.non.celiacc;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RetrievePass extends AppCompatActivity {
    FirebaseAuth auth;
    String emailReset;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_pass);

        auth = FirebaseAuth.getInstance();

        //back to the log in
        Button Backbutton = (Button) findViewById(R.id.btBackToSignIn);
        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
            finish();
            }
        });

        //Reset the email button
        Button Resetbutton = (Button) findViewById(R.id.btReset);
        Resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                email = findViewById(R.id.etEmailReset);
                emailReset= email.getText().toString();
                if (emailReset.equals("")) {
                    Toast.makeText(getApplicationContext(), "אנא הזן אימייל לאיפוס הסיסמא", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    auth.sendPasswordResetEmail(emailReset)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "מייל נשלח לאיפוס סיסמא", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}
