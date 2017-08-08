package edu.bluejack16_2.familysdaily.models;

import java.util.HashMap;

/**
 * Created by fidel on 24-Jul-17.
 */

public class Member {
    private String fullName;
    private boolean confirmed;
    private long createdAt;

    public Member() {
    }

    public Member(HashMap<String, Object> extras) {
        this.fullName = (String)extras.get("fullName");
        this.confirmed = (boolean)extras.get("confirmed");
        this.createdAt = (long)extras.get("createdAt");
    }

    public HashMap<String, Object> toMap(){
        HashMap<String, Object> extras = new HashMap<>();
        extras.put("fullName", fullName);
        extras.put("confirmed", confirmed);
        extras.put("createdAt", createdAt);
        return extras;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
