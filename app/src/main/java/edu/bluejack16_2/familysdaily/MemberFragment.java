package edu.bluejack16_2.familysdaily;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.internal.lv;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.bluejack16_2.familysdaily.models.Member;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemberFragment extends Fragment {

    private ListView lvMember;
    private FirebaseDatabase mDB;

    public MemberFragment() {
        // Required empty public constructor
        mDB = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member, container, false);
        lvMember = (ListView) view.findViewById(R.id.lvMember);
        final MemberListViewAdapter mLVA = new MemberListViewAdapter(getContext());
        /*mLVA.add("Fidelis", "5m ago");
        mLVA.add("Michael", "1h ago");
        mLVA.add("Daniel", "1h ago");
        mLVA.add("Yakub", "1h ago");
        mLVA.add("Regina", "1h ago");
        mLVA.add("Kevin", "1h ago");*/

        Member test = new Member();
        test.setConfirmed(false);
        test.setCreatedAt(Long.parseLong("1500982521745"));
        test.setFullName("test oi");
        //mLVA.add(test, "abcde");
        lvMember.setAdapter(mLVA);
        DatabaseReference membersRef = mDB.getReference("Members");
        membersRef.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mLVA.refresh();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    for(DataSnapshot subChild : child.getChildren()) {
                        Member member = null;
                        Log.d("rioo", subChild.toString());
                        if(LoginActivity.currGroupID.equals(child.getKey())) {
                            Log.d("rioo2", "nambah");
                            member = subChild.getValue(Member.class);
                            if(LoginActivity.currUserID.equals(subChild.getKey())&&!member.isConfirmed()){

                            }
                            else{
                                mLVA.add(member, subChild.getKey());
                                mLVA.notifyDataSetChanged();
                                lvMember.invalidateViews();
                            }
                        }
                        /*else if(!currGroupID.equals("")&&!currGroupID.equals(child.getKey())){
                            return;
                        }*/
                    }
                    Log.d("rioo3", child.toString());
                    Log.d("rioo4", Long.toString(child.getChildrenCount()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Toast.makeText(getContext(), "Jalan", Toast.LENGTH_SHORT).show();
        /*lvMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_SHORT).show();

            }
        });*/
        return view;
    }

}
