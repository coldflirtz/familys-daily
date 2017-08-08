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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import edu.bluejack16_2.familysdaily.models.Group;
import edu.bluejack16_2.familysdaily.models.Member;

public class CreateJoinGroupActivity extends AppCompatActivity {

    EditText etGroup;
    Button btnSubmit, btnJoin;
    TextView tvGroupName;
    RadioButton rbCreateGroup, rbJoinGroup;
    ImageView ivGroupImage;
    RelativeLayout rlFoundGroup;
    Group currGroup;
    FirebaseDatabase mDB;
    String currGroupKey, newGroupCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_join_group);
        init();
    }

    private void init(){
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        ivGroupImage = (ImageView) findViewById(R.id.ivGroupImage);
        tvGroupName = (TextView) findViewById(R.id.tvGroupName);
        etGroup = (EditText) findViewById(R.id.etGroupName);
        rbCreateGroup = (RadioButton) findViewById(R.id.rbCreateGroup);
        rbJoinGroup = (RadioButton) findViewById(R.id.rbJoinGroup);
        rlFoundGroup = (RelativeLayout) findViewById(R.id.foundGroup);
        mDB = FirebaseDatabase.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onSubmitClicked();
        onJoinClicked();
    }

    private void onSubmitClicked(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to db
                if(rbJoinGroup.isChecked()){
                    checkGroup(true);
                }
                else if(rbCreateGroup.isChecked()){
                    addGroup();
                }
            }
        });
    }

    private void onJoinClicked(){
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinGroup(false);
            }
        });
    }

    private void joinGroup(final boolean confirm){
        final FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference usersRef = mDB.getReference("Users");
        usersRef.orderByChild("email").equalTo(currUser.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String userKey = child.getKey();
                    DatabaseReference membersRef;
                    membersRef = mDB.getReference("Members");
                    HashMap<String, Object> extras = new HashMap<>();
                    extras.put("fullName", currUser.getDisplayName());
                    extras.put("confirmed", confirm);
                    extras.put("createdAt", System.currentTimeMillis());
                    Member member = new Member(extras);
                    membersRef.child(currGroupKey).child(userKey).setValue(member);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String generateCode(){
        String code = "";
        Random ran = new Random();
        for(int i=0; i<5; i++){
            code += (char)((ran.nextInt(2) == 0)?(ran.nextInt(26)+'a'):(ran.nextInt(26)+'A'));
        }
        return code;
    }

    private void addGroup(){

        //create new group node
        final DatabaseReference groupsRef = mDB.getReference("Groups");
        groupsRef.orderByChild("groupCode").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean duplicated;
                do{
                    duplicated = false;
                    newGroupCode = generateCode();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Group tempGroup = child.getValue(Group.class);
                        if (tempGroup.getGroupCode().equals(newGroupCode)) {
                            duplicated = true;
                        }
                    }
                }while(duplicated);
                currGroupKey = groupsRef.push().getKey();
                Bundle groupDetail = new Bundle();
                groupDetail.putString("groupName",etGroup.getText().toString());
                groupDetail.putString("groupCode",newGroupCode);
                Group newGroup = new Group(groupDetail);
                groupsRef.child(currGroupKey).setValue(newGroup);

                //add current active user to group
                checkGroup(false);
                joinGroup(true);
                Toast.makeText(CreateJoinGroupActivity.this, "Group Created!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void checkGroup(final boolean showResult){
        DatabaseReference mDB = FirebaseDatabase.getInstance().getReference("Groups");
        mDB.orderByChild("groupCode").equalTo(etGroup.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        currGroup = child.getValue(Group.class);
                        currGroupKey = child.getKey();
                        if(showResult) {
                            showGroup();
                        }
                    }
                    Toast.makeText(CreateJoinGroupActivity.this, "Joined!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(CreateJoinGroupActivity.this, "Group Not Found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showGroup(){
        rlFoundGroup.setVisibility(View.VISIBLE);
        Glide.with(this).load(currGroup.getGroupImgUrl())
                .crossFade().thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivGroupImage);
        tvGroupName.setText(currGroup.getGroupName());
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
