package edu.bluejack16_2.familysdaily;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by fidel on 11-Jul-17.
 */

public class MemberListViewAdapter extends BaseAdapter{
    ArrayList<String> userDetails, userTimes;
    Context context;

    public MemberListViewAdapter(Context context) {
        userDetails = new ArrayList<>();
        userTimes = new ArrayList<>();
        this.context = context;
    }

    public void add(String userDetail, String userTime){
        userDetails.add(userDetail);
        userTimes.add(userTime);
    }

    @Override
    public int getCount() {
        return userDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return userDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview_row_member, parent, false);
        }
        TextView tvUserDetail = (TextView) convertView.findViewById(R.id.tvUserDetail);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);

        tvUserDetail.setText(userDetails.get(position));
        tvTime.setText(userTimes.get(position));

        return convertView;
    }
}
