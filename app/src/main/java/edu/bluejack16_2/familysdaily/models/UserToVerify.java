package edu.bluejack16_2.familysdaily.models;

import android.os.Bundle;


/**
 * Created by fidel on 13-Jul-17.
 */
public class UserToVerify {

    private String email, fName, lName, gender, dob, phone, ppUrl, pwd, verNumber;

    public UserToVerify(){

    }

    public UserToVerify(Bundle extras) {
        this.email = extras.getString("email");
        this.fName = extras.getString("fName");
        this.lName = extras.getString("lName");
        this.gender = extras.getString("gender");
        this.dob = extras.getString("dob");
        this.phone = extras.getString("phone");
        this.ppUrl = extras.getString("ppUrl");
        this.pwd = extras.getString("pwd");
        this.verNumber = extras.getString("verNumber");
    }

    public Bundle toBundle(){
        Bundle extras = new Bundle();
        extras.putString("email", email);
        extras.putString("fName", fName);
        extras.putString("lName", lName);
        extras.putString("gender", gender);
        extras.putString("dob", dob);
        extras.putString("ppUrl", ppUrl);
        extras.putString("pwd", pwd);
        extras.putString("verNumber", verNumber);
        return extras;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPpUrl() {
        return ppUrl;
    }

    public void setPpUrl(String ppUrl) {
        this.ppUrl = ppUrl;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getVerNumber() {
        return verNumber;
    }

    public void setVerNumber(String verNumber) {
        this.verNumber = verNumber;
    }
}
