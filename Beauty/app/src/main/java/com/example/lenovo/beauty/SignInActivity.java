package com.example.lenovo.beauty ;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignInActivity extends AppCompatActivity{
    EditText email, pass;
    Button sign_in,sign_up;
    String smail,spass;
    private FirebaseAuth mAuth;
    public static final String TAG ="com" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        sign_in=findViewById(R.id.btn_login);
        sign_up=findViewById(R.id.btn_signup);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

    }


    public void login(View view) {
        smail=email.getText().toString();
        spass=pass.getText().toString();

        if (TextUtils.isEmpty(smail)){
            Toast.makeText(this, "Please enter the email address", Toast.LENGTH_SHORT).show();
        }


        if (TextUtils.isEmpty(spass)){
            Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT).show();
        }
        mAuth.signInWithEmailAndPassword(smail,spass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent=new Intent(SignInActivity.this,MainActivity.class);
                            startActivity(intent);
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                            Toast.makeText(SignInActivity.this, "Password / Email incorrect", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });


    }

    public void register(View view) {

        smail=email.getText().toString();
        spass=pass.getText().toString();

        if (TextUtils.isEmpty(smail)){
            Toast.makeText(this, "Please enter the email address", Toast.LENGTH_SHORT).show();
        }


        if (TextUtils.isEmpty(spass)){
            Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT).show();
        }

        if (spass.length()<6){
            Toast.makeText(this, "Password length should be more than 6", Toast.LENGTH_SHORT).show();
        }
        mAuth.createUserWithEmailAndPassword(smail,spass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                     @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Toast.makeText(SignInActivity.this, "Okay.. Press Login", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }
}
/*
public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {
    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "MainActivity";
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;
    private TextView mtextStatus, mEmail;
    private ImageView profilePic;
    private LinearLayout mLinearLayout;
    private Button sign_out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        sign_out = (Button) findViewById(R.id.signout);

        mtextStatus = (TextView) findViewById(R.id.displayName);
        mEmail = (TextView) findViewById(R.id.email);
        profilePic = (ImageView) findViewById(R.id.displayPic);
        mLinearLayout = (LinearLayout) findViewById(R.id.detailsLayout);
        mLinearLayout.setVisibility(View.GONE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        sign_out.setOnClickListener(this);
        if (isNetwork()) {
            signIn();
        } else {
            noOnline();
        }

    }
    public void signout(View view) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                        mLinearLayout.setVisibility(View.GONE);

                    }
                });

    }
    private boolean isNetwork() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null;

    }

    private void noOnline() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("open settings to connect to the internet");
        builder.setTitle("Internet connection is not available");
        builder.setPositiveButton("goto settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        }).show();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.signout:
                signOut();
                break;

        }


    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // Write whatever you like to notify the user
                        mLinearLayout.setVisibility(View.GONE);

                    }
                });
    }



    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            // Signed in successfully, show authenticated UI.
            mLinearLayout.setVisibility(View.VISIBLE);
            GoogleSignInAccount acct = result.getSignInAccount();
            String email = acct.getEmail().toString();
            mtextStatus.setText(""+acct.getDisplayName());
            mEmail.setText(""+email);
            Uri pic = acct.getPhotoUrl();
            Glide.with(this).load(pic).into(profilePic);
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);

        } else {
            // Signed out, show unauthenticated UI.

        }
    }


}
*/
