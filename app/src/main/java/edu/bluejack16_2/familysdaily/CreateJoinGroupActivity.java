package edu.bluejack16_2.familysdaily;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateJoinGroupActivity extends AppCompatActivity {

    EditText etGroup;
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_join_group);
        init();
        onSubmitClicked();
    }

    private void init(){
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        etGroup = (EditText) findViewById(R.id.etGroupName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
