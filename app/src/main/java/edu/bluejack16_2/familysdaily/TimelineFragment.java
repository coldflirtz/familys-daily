package edu.bluejack16_2.familysdaily;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.bluejack16_2.familysdaily.models.Note;
import edu.bluejack16_2.familysdaily.models.Product;
import edu.bluejack16_2.familysdaily.models.Schedule;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {

    ListView lvNotes, lvExpired, lvSchedules;
    FirebaseDatabase mDB;


    public TimelineFragment() {
        // Required empty public constructor
        init();
    }

    public void init(){
        mDB = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        lvNotes = (ListView) view.findViewById(R.id.lvNotes);
        final NotesListViewAdapter nLVA = new NotesListViewAdapter(getContext());

        lvExpired = (ListView) view.findViewById(R.id.lvExpired);
        final ExpiredListViewAdapter eLVA = new ExpiredListViewAdapter(getContext());

        lvSchedules = (ListView) view.findViewById(R.id.lvSchedules);
        final SchedulesListViewAdapter sLVA = new SchedulesListViewAdapter(getContext());

        DatabaseReference notesRef = mDB.getReference("Notes");
        notesRef.orderByKey().equalTo(LoginActivity.currUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nLVA.refresh();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    for(DataSnapshot subChild : child.getChildren()) {
                        if(subChild.exists()) {
                            //Toast.makeText(getContext(), "Note subChild Value: "+subChild.getValue(Note.class), Toast.LENGTH_SHORT).show();
                            Note note = subChild.getValue(Note.class);
                            nLVA.add(note, subChild.getKey());
                        }
                    }
                }
                lvNotes.invalidateViews();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lvNotes.setAdapter(nLVA);
        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note currNote = nLVA.getItem(position);
                Intent intent = new Intent(getContext(), ModifyNoteActivity.class);
                intent.putExtras(currNote.toBundle());
                intent.putExtra("noteKey", nLVA.getKey(position));
                startActivity(intent);
            }
        });

        DatabaseReference expiredRef = mDB.getReference("ExpiredDate");
        expiredRef.orderByKey().equalTo(LoginActivity.currGroupID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eLVA.refresh();
                //Toast.makeText(getContext(), "datasnapshop.getkey(): "+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    //Toast.makeText(getContext(), "child: "+child.getKey(), Toast.LENGTH_SHORT).show();
                    for(DataSnapshot subChild : child.getChildren()) {
                        //Toast.makeText(getContext(), "subchild: "+subChild.getKey(), Toast.LENGTH_SHORT).show();
                        for(DataSnapshot subChild2 : subChild.getChildren()) {
                            //Toast.makeText(getContext(), "subchild2: "+subChild2.getKey()+" getValue(): "+ subChild2.getValue(Product.class), Toast.LENGTH_SHORT).show();
                            if(subChild2.exists()) {
                                Product product = subChild2.getValue(Product.class);
                                eLVA.add(product, subChild2.getKey());
                            }
                        }
                    }
                }
                lvExpired.invalidateViews();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lvExpired.setAdapter(eLVA);
        lvExpired.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product currProduct = eLVA.getItem(position);
                Intent intent = new Intent(getContext(), ModifyExpiredActivity.class);
                intent.putExtras(currProduct.toBundle());
                intent.putExtra("productKey", eLVA.getKey(position));
                startActivity(intent);
            }
        });


        DatabaseReference schedulesRef = mDB.getReference("Schedules");
        Log.d("Schedules", LoginActivity.currGroupID);
        schedulesRef.orderByKey().equalTo(LoginActivity.currGroupID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sLVA.refresh();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    for (DataSnapshot subChild : child.getChildren()) {
                        for(DataSnapshot subChild2 : subChild.getChildren()) {
                            if(subChild2.exists()) {
                                Log.d("schedules", subChild2.toString());
                                Schedule schedule = subChild2.getValue(Schedule.class);
                                sLVA.add(schedule, subChild2.getKey());
                            }
                        }
                    }
                }
                lvSchedules.invalidateViews();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lvSchedules.setAdapter(sLVA);
        lvSchedules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Schedule currSchedule = sLVA.getItem(position);
                Intent intent = new Intent(getContext(), ModifyScheduleActivity.class);
                intent.putExtras(currSchedule.toBundle());
                intent.putExtra("scheduleKey", sLVA.getKey(position));
                startActivity(intent);
            }
        });


        ///Toast.makeText(getContext(), "set Expired", Toast.LENGTH_SHORT).show();
        //lvExpired.setAdapter(eLVA);
        //Toast.makeText(getContext(), "set Notes", Toast.LENGTH_SHORT).show();
        //lvNotes.setAdapter(nLVA);
        //Toast.makeText(getContext(), "set Schedule", Toast.LENGTH_SHORT).show();
        //lvSchedules.setAdapter(sLVA);

        //setNotesListViewHeightBasedOnChildren(lvNotes);
        //setSchedulesListViewHeightBasedOnChildren(lvSchedules);
        //setExpiredListViewHeightBasedOnChildren(lvExpired);

        return view;
    }

}
