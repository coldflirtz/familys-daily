package edu.bluejack16_2.familysdaily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.bluejack16_2.familysdaily.models.User;
import edu.bluejack16_2.familysdaily.models.UserToVerify;

public class VerificationActivity extends AppCompatActivity {

    Button btnVerify;
    EditText etConfCode;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        init();
    }

    private void init(){
        btnVerify = (Button) findViewById(R.id.btnVerify);
        etConfCode = (EditText) findViewById(R.id.etConfCode);
        firebaseAuth = FirebaseAuth.getInstance();
        onVerifyClicked();
    }

    private void onVerifyClicked(){
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
    }

    private void verify(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference mDB = FirebaseDatabase.getInstance().getReference("UserToVerify");
        mDB.orderByChild("email").equalTo(firebaseUser.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("verifydb", dataSnapshot.toString());
                if(dataSnapshot.exists()){
                    for(DataSnapshot child : dataSnapshot.getChildren()) {
                        UserToVerify userToVerify = child.getValue(UserToVerify.class);
                        if (userToVerify.getVerNumber().equals(etConfCode.getText().toString())) {
                            //pindahin data ke tabel users
                            DatabaseReference mDB = FirebaseDatabase.getInstance().getReference("Users");
                            mDB.push().setValue(new User(userToVerify.toBundle()));
                            dataSnapshot.getRef().removeValue();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
