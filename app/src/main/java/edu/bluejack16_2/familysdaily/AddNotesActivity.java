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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.bluejack16_2.familysdaily.models.Group;
import edu.bluejack16_2.familysdaily.models.Note;
import edu.bluejack16_2.familysdaily.models.Product;
import edu.bluejack16_2.familysdaily.models.User;

public class AddNotesActivity extends AppCompatActivity {

    Spinner option;
    EditText notes, expiredName, expiredDate;
    TextView or;
    Button btnSubmit, btnTakePhoto, btnSubmitExpired, btnBrowsePhoto;
    FirebaseDatabase mDB;
    FirebaseAuth firebaseAuth;
    Boolean isNotes;

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
        btnBrowsePhoto = (Button) findViewById(R.id.btnAddNotesBrowsePhoto);
        mDB = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
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
                        isNotes = true;
                    }else if(option.getSelectedItem().equals("Expired Date")){
                        setVisibility(View.GONE, View.VISIBLE);
                        isNotes = false;
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
        btnBrowsePhoto.setVisibility(expired);
        btnTakePhoto.setVisibility(expired);
        btnSubmitExpired.setVisibility(expired);
    }

    private void onNotesSubmitClicked(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to db
                if(isNotes) {
                    addNote();
                }else{
                    addExpired();
                }

                //redirect to main menu
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addExpired(){
        //create new group node
        final DatabaseReference expiredRef = mDB.getReference("ExpiredDate");
        //productName, productExpiredDate, productPrice
        Bundle bundleNote = new Bundle();
        bundleNote.putString("productName", expiredName.getText().toString());
        bundleNote.putString("productExpiredDate", expiredDate.getText().toString());
        //Ini belum ada price soalnya belum bisa foto
        bundleNote.putString("productPrice", "0");

        Product product = new Product(bundleNote);
        String currExpiredKey;
        currExpiredKey = expiredRef.push().getKey();
        expiredRef.child(LoginActivity.currGroupID).child(LoginActivity.currUserID).child(currExpiredKey).setValue(product);
    }

    private void addNote(){

        //create new group node
        DatabaseReference noteRef = mDB.getReference("Notes");

        Long time = System.currentTimeMillis();

        Bundle bundleNote = new Bundle();
        bundleNote.putString("notesDetail", notes.getText().toString());
        bundleNote.putString("notesTime", time.toString());

        Note note = new Note(bundleNote);
        String currNoteKey;
        currNoteKey = noteRef.push().getKey();
        noteRef.child(LoginActivity.currUserID).child(currNoteKey).setValue(note);

    }

    private void onExpiredSubmitClicked(){
        btnSubmitExpired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to db

                addExpired();

                //redirect to main menu
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
