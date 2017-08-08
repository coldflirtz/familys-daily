package edu.bluejack16_2.familysdaily;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import edu.bluejack16_2.familysdaily.models.Schedule;

public class ModifyScheduleActivity extends AppCompatActivity {

    private EditText etScheduleDate, etScheduleDetail, etScheduleLocation;
    private Button btnUpdate, btnDelete;
    private Schedule currSchedule;
    private String currScheduleKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_schedule);
        init();
        fillField();
    }

    private void init(){
        etScheduleDate = (EditText) findViewById(R.id.etModifyScheduleDate);
        etScheduleDetail = (EditText) findViewById(R.id.etModifyScheduleDetail);
        etScheduleLocation = (EditText) findViewById(R.id.etModifyScheduleLocation);
        btnUpdate = (Button) findViewById(R.id.btnUpdateSchedule);
        btnDelete = (Button) findViewById(R.id.btnDeleteSchedule);

        currSchedule = new Schedule(getIntent().getExtras());
        currScheduleKey = getIntent().getStringExtra("scheduleKey");
        onBtnUpdateClicked();
        onBtnDeleteClicked();
    }

    private void fillField(){
        etScheduleDate.setText(currSchedule.getScheduleTime());
        etScheduleDetail.setText(currSchedule.getScheduleDetail());
    }

    private void onBtnUpdateClicked(){

    }

    private void onBtnDeleteClicked(){

    }

}
