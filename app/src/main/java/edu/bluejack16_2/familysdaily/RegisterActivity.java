package edu.bluejack16_2.familysdaily;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import edu.bluejack16_2.familysdaily.models.User;
import edu.bluejack16_2.familysdaily.models.UserToVerify;

public class RegisterActivity extends AppCompatActivity {

    EditText etDOB;
    Spinner spGender;
    Button btnReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init(){
        etDOB = (EditText)findViewById(R.id.etDOB);
        spGender = (Spinner) findViewById(R.id.spGender);
        btnReg = (Button)findViewById(R.id.btnReg);
        setGenderSpinner();
        showDPPopup();
        register();
        onRegisterClicked();
    }

    private void showDPPopup(){
        etDOB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment dateFragment = new DatePickerFragment();

                dateFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }


    private void setGenderSpinner(){
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, new String[]{"Gender", "Male","Female"});
        spGender.setAdapter(spinnerAdapter);
        /*View v = spGender.getSelectedView();
        ((TextView) v).setTextColor(Color.WHITE);
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    private void onRegisterClicked(){
        btnReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register(){
        Intent currIntent = getIntent();
        if(currIntent.getExtras()!=null) {
            Bundle extras = currIntent.getExtras();
            addUser(extras);
        }
    }

    private void addUser(Bundle extras){
        Random ran = new Random();
        String verNumber = Integer.toString(100000+ran.nextInt(900000));
        Toast.makeText(this, "Registering...", Toast.LENGTH_SHORT).show();
        extras.putString("verNumber", verNumber);
        UserToVerify userToVerify = new UserToVerify(extras);
        DatabaseReference mDB = FirebaseDatabase.getInstance().getReference("UserToVerify");
        mDB.push().setValue(userToVerify);
        //kirim email
        goToVerification(extras);
    }

    private void goToVerification(Bundle extras){
        Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
        startActivity(intent);
    }

}