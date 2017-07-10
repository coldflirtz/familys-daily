package edu.bluejack16_2.familysdaily;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
        Intent intent = new Intent(getApplicationContext(),VerificationActivity.class);
        //intent.putExtra("tag",value);
        startActivity(intent);
    }


}