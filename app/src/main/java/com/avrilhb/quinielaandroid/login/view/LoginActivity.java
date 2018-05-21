package com.avrilhb.quinielaandroid.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avrilhb.quinielaandroid.R;
import com.avrilhb.quinielaandroid.login.presenter.LoginPresenter;
import com.avrilhb.quinielaandroid.login.presenter.LoginPresenterImpl;
import com.avrilhb.quinielaandroid.view.ContainerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView{

    @BindView(R.id.txtCreateAccount) TextView txtCreateAccount;
    @BindView(R.id.txtForgotPassword) TextView txtForgotPassword;
    @BindView(R.id.btnLogin) TextView btnLogin;
    @BindView(R.id.txtUser) EditText txtUser;
    @BindView(R.id.txtPassword) EditText txtPassword;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "LoginActivity";
    private String password;
    private String email;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private boolean validaData = false;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        presenter = new LoginPresenterImpl(this);

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

    @OnClick (R.id.btnLogin)
    public void signUp(View view) {
        email = txtUser.getText().toString();
        password = txtPassword.getText().toString();

        validateData(email, password);

        if(validaData){
            signUp(email, password);
        }
    }

    private void validateData(String email, String password) {
        if(!email.equals("") && !password.equals("")){
            if(!email.matches(emailPattern)){
                Toast.makeText(this, "Por favor introduce un email válido", Toast.LENGTH_SHORT).show();
            }else{
                validaData = true;
            }
        }else{
            validaData = false;
            Toast.makeText(this, "Existen campos vacíos", Toast.LENGTH_SHORT).show();
        }

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
    public void signUp(String email, String password) {
        presenter.signUp(email, password, this, firebaseAuth);
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
