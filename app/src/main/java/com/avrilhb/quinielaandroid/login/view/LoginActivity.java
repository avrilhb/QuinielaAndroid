package com.avrilhb.quinielaandroid.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.avrilhb.quinielaandroid.R;
import com.avrilhb.quinielaandroid.view.ContainerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView{

    @BindView(R.id.txtCreateAccount) TextView txtCreateAccount;
    @BindView(R.id.txtForgotPassword) TextView txtForgotPassword;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + firebaseUser.getUid());
                    goMenu();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }

    @Override
    public void createAccount(View view) {
        Intent j = new Intent(this, CreateAccountActivity.class);
        startActivity(j);
    }

    @Override
    public void recoverPassword(View view) {
        Intent i = new Intent(this, RecoverPasswordActivity.class);
        startActivity(i);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void loginError(String error) {

    }

    @Override
    public void enableInput() {

    }

    @Override
    public void disableInput() {

    }

    @Override
    public void goMenu() {
        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
