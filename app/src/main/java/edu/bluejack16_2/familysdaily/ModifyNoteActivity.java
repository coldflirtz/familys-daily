package edu.bluejack16_2.familysdaily;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.bluejack16_2.familysdaily.models.Note;

public class ModifyNoteActivity extends AppCompatActivity {

    private EditText etUpdateNote;
    private Button btnUpdate, btnDelete;
    private Note currNote;
    private String currNoteKey;
    private FirebaseDatabase mDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_note);
        init();
        fillField();
    }

    private void init(){
        etUpdateNote = (EditText) findViewById(R.id.etUpdateNote);
        btnUpdate = (Button) findViewById(R.id.btnUpdateNote);
        btnDelete = (Button) findViewById(R.id.btnDeleteNote);
        currNote = new Note(getIntent().getExtras());
        currNoteKey = getIntent().getStringExtra("noteKey");
        mDB = FirebaseDatabase.getInstance();
        onBtnUpdateClicked();
        onBtnDeleteClicked();
    }

    private void fillField(){
        etUpdateNote.setText(currNote.getNotesDetail());
        Toast.makeText(this, currNoteKey, Toast.LENGTH_SHORT).show();
    }

    private void onBtnUpdateClicked(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote();
            }
        });
    }

    private void updateNote(){
        Long time = System.currentTimeMillis();
        currNote.setNotesDetail(etUpdateNote.getText().toString());
        currNote.setNotesTime(time.toString());

        DatabaseReference notesRef = mDB.getReference("Notes");
        notesRef.child(LoginActivity.currUserID).child(currNoteKey).setValue(currNote);
        Toast.makeText(ModifyNoteActivity.this, "Note Updated!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void onBtnDeleteClicked(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteAlertBuilder = new AlertDialog.Builder(ModifyNoteActivity.this);
                deleteAlertBuilder.setMessage("Delete note ?");
                deleteAlertBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteNote();
                    }
                });
                deleteAlertBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ModifyNoteActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog deleteAlert = deleteAlertBuilder.create();
                deleteAlert.show();
            }
        });
    }

    private void deleteNote(){
        DatabaseReference notesRef = mDB.getReference("Notes");
        notesRef.child(LoginActivity.currUserID).child(currNoteKey).getRef().removeValue();
        Toast.makeText(ModifyNoteActivity.this, "Note Removed!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
