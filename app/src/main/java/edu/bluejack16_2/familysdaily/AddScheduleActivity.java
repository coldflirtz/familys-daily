package edu.bluejack16_2.familysdaily;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.bluejack16_2.familysdaily.models.MapsFragment;
import edu.bluejack16_2.familysdaily.models.Schedule;

public class AddScheduleActivity extends AppCompatActivity {

    Button btnSubmit;
    EditText etScheduleDate, etScheduleDetail, etScheduleLocation;
    FirebaseDatabase mDB;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        firebaseAuth = FirebaseAuth.getInstance();
        init();
        clickEvent();
    }

    private void init(){
        btnSubmit = (Button) findViewById(R.id.btnAddScheduleSubmit);
        etScheduleDate = (EditText) findViewById(R.id.etAddScheduleDate);
        etScheduleDetail = (EditText) findViewById(R.id.etAddScheduleDetail);
        etScheduleLocation = (EditText) findViewById(R.id.etAddScheduleLocation);
        mDB = FirebaseDatabase.getInstance();
    }

    public void clickEvent(){
        onSubmitClicked();
        onScheduleDateClicked();
        onScheduleLocationClicked();
    }

    private void onSubmitClicked(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to db
                addSchedule();

                //redirect to main menu
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addSchedule(){
        //create new group node
        final DatabaseReference scheduleRef = mDB.getReference("Schedules");

        Bundle bundleNote = new Bundle();
        bundleNote.putString("scheduleDetail", etScheduleDetail.getText().toString());
        bundleNote.putString("scheduleTime", etScheduleDate.getText().toString());
        bundleNote.putString("scheduleCreator", MainActivity.currUser.getfName()+" "+MainActivity.currUser.getlName());

        Schedule schedule = new Schedule(bundleNote);
        String currScheduleKey;
        currScheduleKey = scheduleRef.push().getKey();
        scheduleRef.child(LoginActivity.currGroupID).child(LoginActivity.currUserID).child(currScheduleKey).setValue(schedule);
    }

    private void onScheduleDateClicked(){
        etScheduleDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment dateFragment = new DatePickerFragment();

                dateFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    private void onScheduleLocationClicked(){
        etScheduleLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }

}
