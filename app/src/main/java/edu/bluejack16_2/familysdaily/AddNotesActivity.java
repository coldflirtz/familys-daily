package edu.bluejack16_2.familysdaily;

import android.content.Intent;
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

public class AddNotesActivity extends AppCompatActivity {

    Spinner option;
    EditText notes, expiredName, expiredDate;
    TextView or;
    Button btnSubmit, btnTakePhoto, btnSubmitExpired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        init();
        setEvent();
    }

    private void init(){
        option = (Spinner) findViewById(R.id.spinnerAddNotesOption);
        notes = (EditText) findViewById(R.id.etAddNotesNote);
        expiredName = (EditText) findViewById(R.id.etAddNotesExpiredDateName);
        expiredDate = (EditText) findViewById(R.id.etAddNotesExpiredDateExpired);
        or = (TextView) findViewById(R.id.tvAddNotesOr);
        btnSubmit = (Button) findViewById(R.id.btnAddNotesSubmit);
        btnTakePhoto = (Button) findViewById(R.id.btnAddNotesTakePhoto);
        btnSubmitExpired = (Button) findViewById(R.id.btnAddNotesExpiredSubmit);

        setOptionSpinner();
    }

    private void setOptionSpinner(){
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, new String[]{"Option", "Notes", "Expired Date"});
        option.setAdapter(spinnerAdapter);
    }

    private void setEvent(){
        spinnerValueChangeEvent();
        onExpiredSubmitClicked();
        onNotesSubmitClicked();
        onExpiredDateClicked();
    }

    private void spinnerValueChangeEvent(){
        option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.equals(option)){
                    if(option.getSelectedItem().equals("Notes")){
                        setVisibility(View.VISIBLE, View.GONE);
                    }else if(option.getSelectedItem().equals("Expired Date")){
                        setVisibility(View.GONE, View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void onExpiredDateClicked(){
        expiredDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment dateFragment = new DatePickerFragment();

                dateFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    private void setVisibility(int note, int expired){
        notes.setVisibility(note);
        btnSubmit.setVisibility(note);
        expiredName.setVisibility(expired);
        expiredDate.setVisibility(expired);
        or.setVisibility(expired);
        btnTakePhoto.setVisibility(expired);
        btnSubmitExpired.setVisibility(expired);
    }

    private void onNotesSubmitClicked(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to db



                //redirect to main menu
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onExpiredSubmitClicked(){
        btnSubmitExpired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to db



                //redirect to main menu
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
