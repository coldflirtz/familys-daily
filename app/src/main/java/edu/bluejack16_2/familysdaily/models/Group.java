package edu.bluejack16_2.familysdaily.models;

/**
 * Created by fidel on 13-Jul-17.
 */

public class Group {
    //Key = Generate ID + Family Name
    public String familyName;

    public User user;

    public Group(){

    }

    public Group(User user, String familyName) {
        this.user = user;
        this.familyName = familyName;
    }
}
