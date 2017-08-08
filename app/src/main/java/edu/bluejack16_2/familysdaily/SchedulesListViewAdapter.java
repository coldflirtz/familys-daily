package edu.bluejack16_2.familysdaily;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.bluejack16_2.familysdaily.models.Schedule;

/**
 * Created by fidel on 31-Jul-17.
 */

public class SchedulesListViewAdapter extends BaseAdapter {
    ArrayList<Schedule> schedules;
    ArrayList<String> scheduleKeys;
    Context context;

    public class ViewHolder{
        TextView tvScheduleDate, tvScheduleDetail, tvScheduleCreator;
    }

    public SchedulesListViewAdapter(Context context){
        schedules = new ArrayList<>();
        scheduleKeys = new ArrayList<>();
        this.context = context;
    }

    public void add(Schedule schedule, String scheduleKey){
        //Toast.makeText(context, "add schedule : "+schedule.getScheduleTime(), Toast.LENGTH_SHORT).show();
        schedules.add(schedule);
        scheduleKeys.add(scheduleKey);
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void refresh(){
        schedules.clear();
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public Schedule getItem(int position) {
        return schedules.get(position);
    }

    public String getKey(int position){
        return scheduleKeys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mainViewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview_row_schedule, parent, false);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.tvScheduleCreator = (TextView) convertView.findViewById(R.id.tvScheduleCreator);
            viewHolder.tvScheduleDate = (TextView) convertView.findViewById(R.id.tvScheduleDate);
            viewHolder.tvScheduleDetail = (TextView) convertView.findViewById(R.id.tvScheduleDetail);

            viewHolder.tvScheduleCreator.setText(schedules.get(position).getScheduleCreator());
            viewHolder.tvScheduleDate.setText(schedules.get(position).getScheduleTime());
            viewHolder.tvScheduleDetail.setText(schedules.get(position).getScheduleDetail());
            convertView.setTag(viewHolder);
        }
        else{
            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.tvScheduleCreator.setText(schedules.get(position).getScheduleCreator());
            mainViewHolder.tvScheduleDate.setText(schedules.get(position).getScheduleTime());
            mainViewHolder.tvScheduleDetail.setText(schedules.get(position).getScheduleDetail());
        }

        return convertView;
    }
}
