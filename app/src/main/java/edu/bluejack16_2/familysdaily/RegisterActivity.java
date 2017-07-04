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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, new String[]{"Gender", "Male","Female"});
        spGender.setAdapter(spinnerAdapter);
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
        Intent intent = new Intent(getApplicationContext(),VerificationActivity.class);
        //intent.putExtra("tag",value);
        startActivity(intent);
    }


}