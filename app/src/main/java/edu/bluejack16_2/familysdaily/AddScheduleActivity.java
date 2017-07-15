package edu.bluejack16_2.familysdaily;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddScheduleActivity extends AppCompatActivity {

    Button btnSubmit;
    EditText etSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        init();
        clickEvent();
    }

    private void init(){
        btnSubmit = (Button) findViewById(R.id.btnAddScheduleSubmit);
        etSchedule = (EditText) findViewById(R.id.etAddScheduleDate);
    }

    public void clickEvent(){
        onSubmitClicked();
        onScheduleClicked();
    }

    private void onSubmitClicked(){
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

    private void onScheduleClicked(){
        etSchedule.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment dateFragment = new DatePickerFragment();

                dateFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }


}
