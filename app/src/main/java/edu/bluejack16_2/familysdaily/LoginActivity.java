package edu.bluejack16_2.familysdaily;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private LoginButton fbLoginBtn;
    private CallbackManager callbackManager;
    private TextView tvTest, tvRegister;
    private FirebaseAuth firebaseAuth;

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
        firebaseAuth = FirebaseAuth.getInstance();
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
        }
        else{
            tvTest.setText(null);
        }
    }

    private void handleFacebookAccessToken(AccessToken token){
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
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
    }
}
