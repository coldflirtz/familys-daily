package edu.bluejack16_2.familysdaily.models;

import android.os.Bundle;

/**
 * Created by fidel on 13-Jul-17.
 */

public class Note {
    private String notesDetail, notesTime;

    public Note(){

    }

    public Note(Bundle note) {
        this.notesDetail = note.getString("notesDetail");
        this.notesTime = note.getString("notesTime");
    }

    public Bundle toBundle() {
        Bundle note = new Bundle();
        note.putString("notesDetail", notesDetail);
        note.putString("notesTime", notesTime);
        return note;
    }

    public String getNotesDetail() {
        return notesDetail;
    }

    public void setNotesDetail(String notesDetail) {
        this.notesDetail = notesDetail;
    }

    public String getNotesTime() {
        return notesTime;
    }

    public void setNotesTime(String notesTime) {
        this.notesTime = notesTime;
    }
}
