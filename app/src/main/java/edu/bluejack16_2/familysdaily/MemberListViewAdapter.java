package edu.bluejack16_2.familysdaily;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import edu.bluejack16_2.familysdaily.models.Member;

/**
 * Created by fidel on 11-Jul-17.
 */

public class MemberListViewAdapter extends BaseAdapter{
    ArrayList<String> userNames, requestTimes, userIDs;
    ArrayList<Boolean> statuses;
    Context context;
    FirebaseDatabase mDB;

    public class ViewHolder{
        TextView tvUserName, tvTime;
        Button btnConfirm, btnReject;
        ImageView ivUserPicture;
    }

    public MemberListViewAdapter(Context context) {
        userNames = new ArrayList<>();
        requestTimes = new ArrayList<>();
        userIDs = new ArrayList<>();
        statuses = new ArrayList<>();
        mDB = FirebaseDatabase.getInstance();
        this.context = context;
    }

    public void add(Member member, String userID){
        userIDs.add(userID);
        userNames.add(member.getFullName());
        Log.d("member", member.getFullName());
        requestTimes.add((String)DateUtils.getRelativeTimeSpanString(member.getCreatedAt(), System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS));
        statuses.add(member.isConfirmed());
        //Toast.makeText(context, "tambah", Toast.LENGTH_SHORT).show();
    }

    public void refresh(){
        userIDs.clear();
        userNames.clear();
        requestTimes.clear();
        statuses.clear();
    }

    @Override
    public int getCount() {
        return userNames.size();
    }

    @Override
    public Object getItem(int position) {
        return userNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public String getUserID(int position){
        return userIDs.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mainViewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview_row_member, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            viewHolder.btnConfirm = (Button) convertView.findViewById(R.id.btnConfirm);
            viewHolder.btnReject = (Button) convertView.findViewById(R.id.btnReject);
            viewHolder.ivUserPicture = (ImageView) convertView.findViewById(R.id.ivUserPicture);

            viewHolder.tvUserName.setText(userNames.get(position));
            viewHolder.tvTime.setText(requestTimes.get(position));
            if(statuses.get(position)) {
                viewHolder.btnConfirm.setVisibility(View.GONE);
                viewHolder.btnReject.setVisibility(View.GONE);
            }

            final DatabaseReference membersRef = mDB.getReference("Members");

            viewHolder.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    membersRef.child(LoginActivity.currGroupID).orderByKey().equalTo(getUserID(position)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot child : dataSnapshot.getChildren()){
                                Log.d("mikel", child.toString());
                                Member temp = child.getValue(Member.class);
                                temp.setConfirmed(true);
                                child.getRef().setValue(temp);

                            }
                            Log.d("mlva","Member confirm");
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(context, "Button confirm no "+position+" kepencet", Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    membersRef.child(LoginActivity.currGroupID).orderByKey().equalTo(getUserID(position)).getRef().removeValue();
                    Log.d("mlva","Member reject");
                    notifyDataSetChanged();
                    Toast.makeText(context, "Button reject no "+position+" kepencet", Toast.LENGTH_SHORT).show();
                }
            });


            convertView.setTag(viewHolder);

        }
        else{
            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.tvUserName.setText(userNames.get(position));
            mainViewHolder.tvTime.setText(requestTimes.get(position));
            if(statuses.get(position)) {
                mainViewHolder.btnConfirm.setVisibility(View.GONE);
                mainViewHolder.btnReject.setVisibility(View.GONE);
            }
        }

        return convertView;
    }
}
