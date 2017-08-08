package edu.bluejack16_2.familysdaily;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by fidel on 31-Jul-17.
 */

public class FCMInitializationService extends FirebaseInstanceIdService{

    FirebaseDatabase mDB;




    private static final String TAG = "MyFirebaseIIDService";

    FCMInitializationService(){
        mDB  = FirebaseDatabase.getInstance();
    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        DatabaseReference usersRef = mDB.getReference("Users");

        usersRef.child(LoginActivity.currUserID).child("fcmToken").setValue(token);
    }


}
