package edu.bluejack16_2.familysdaily.models;

import android.os.Bundle;

/**
 * Created by fidel on 13-Jul-17.
 */

public class Group {
    //Key = Generate ID + Family Name
    private String groupName, groupImgUrl, groupCode;

    public Group(){

    }

    public Group(Bundle groupDetail) {
        this.groupName = groupDetail.getString("groupName");
        this.groupImgUrl = groupDetail.getString("groupImgUrl");
        this.groupCode = groupDetail.getString("groupCode");
    }

    public Bundle toBundle(){
        Bundle groupDetail = new Bundle();
        groupDetail.putString("groupName", groupName);
        groupDetail.putString("groupImgUrl", groupImgUrl);
        groupDetail.putString("groupCode", groupCode);
        return groupDetail;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupImgUrl() {
        return groupImgUrl;
    }

    public void setGroupImgUrl(String groupImgUrl) {
        this.groupImgUrl = groupImgUrl;
    }
}
