package edu.bluejack16_2.familysdaily;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private LoginButton fbLoginBtn;
    private SignInButton googleLoginBtn;
    private CallbackManager callbackManager;
    private TextView tvTest, tvRegister;
    private FirebaseAuth firebaseAuth;
    private GoogleApiClient googleApiClient;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        fbLogin();
        if(AccessToken.getCurrentAccessToken()!=null){
            tvTest.setText("Access Token : "+AccessToken.getCurrentAccessToken().getToken()+"\nToken UID Cons : "+AccessToken.getCurrentAccessToken().getUserId());
        }
        goToRegister();
    }

    private void init(){
        callbackManager = CallbackManager.Factory.create();
        fbLoginBtn = (LoginButton) findViewById(R.id.fbLoginBtn);
        tvTest = (TextView) findViewById(R.id.tvTest);
        tvRegister = (TextView)findViewById(R.id.tvRegister);
        fbLoginBtn.setReadPermissions("email", "public_profile");
        googleLoginBtn = (SignInButton) findViewById(R.id.googleLoginBtn);
        googleLoginBtn.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.google_web_client_id)).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        Toast.makeText(this, "init success", Toast.LENGTH_SHORT).show();
    }

    private void fbLogin(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //tvTest.setText("Access Token : "+loginResult.getAccessToken().getToken()+"\nToken UID : "+ loginResult.getAccessToken().getUserId()+"\nToken UID Cons : "+AccessToken.getCurrentAccessToken().getUserId());
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Login Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void googleLogin(){
        Intent loginIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(loginIntent, RC_SIGN_IN);
    }

    private void goToRegister(){
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user){
        if(user != null){
            tvTest.setText(user.getDisplayName()+"\n"+user.getUid());
            /*String userProvider = user.getProviderId();
            if(userProvider == "facebook.com") {
                for(UserInfo userInfo : user.getProviderData()){
                    GraphRequest request = GraphRequest.newMeRequest();
                }

            }else if(userProvider == "google.com"){

            }*/

        }
        else{
            tvTest.setText(null);
        }
    }

    private void handleFacebookAccessToken(final AccessToken token){
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback(){

                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {

                        }
                    });
                    updateUI(user);
                }
                else{
                    updateUI(null);
                    tvTest.setText("Login failure. "+task.getException());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else {
                updateUI(null);
            }
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account){
        Log.d(TAG, "firebaseAuthWithGoogle:"+account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

            }
        });
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i==R.id.googleLoginBtn){
            googleLogin();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Play Service error.", Toast.LENGTH_SHORT).show();
    }
}
