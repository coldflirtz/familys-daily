package edu.bluejack16_2.familysdaily.models;

import android.os.Bundle;

/**
 * Created by fidel on 13-Jul-17.
 */

public class Schedule {
    private String scheduleCreator, scheduleDetail, scheduleTime;

    public Schedule(){

    }

    public Schedule(Bundle schedule) {
        this.scheduleDetail = schedule.getString("scheduleDetail");
        this.scheduleTime = schedule.getString("scheduleTime");
        this.scheduleCreator = schedule.getString("scheduleCreator");
    }

    public Bundle toBundle() {
        Bundle schedule = new Bundle();
        schedule.putString("scheduleDetail", scheduleDetail);
        schedule.putString("scheduleTime", scheduleTime);
        schedule.putString("scheduleCreator", scheduleCreator);
        return schedule;
    }

    public String getScheduleDetail() {
        return scheduleDetail;
    }

    public void setScheduleDetail(String scheduleDetail) {
        this.scheduleDetail = scheduleDetail;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getScheduleCreator() {
        return scheduleCreator;
    }

    public void setScheduleCreator(String scheduleCreator) {
        this.scheduleCreator = scheduleCreator;
    }
}
