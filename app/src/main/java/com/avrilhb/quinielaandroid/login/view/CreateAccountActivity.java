package com.avrilhb.quinielaandroid.login.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avrilhb.quinielaandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateAccountActivity extends AppCompatActivity {

    @BindView(R.id.txtTitleTool) TextView txtTitleTool;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.edtUserName) EditText edtUserName;
    @BindView(R.id.edtUserMail) EditText edtUserMail;
    @BindView(R.id.edtPassword) EditText edtPassword;
    @BindView(R.id.edtConfirmPassword) EditText edtConfirmPassword;
    @BindView(R.id.btnJoin) Button btnJoin;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "CreateAccountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ButterKnife.bind(this);

        showToolbar(true);
        txtTitleTool.setText(getResources().getString(R.string.create_account));

        firebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    FirebaseCrash.logcat(Log.WARN, TAG, "usuario logueado " + firebaseUser.getEmail());
                }else{
                    FirebaseCrash.logcat(Log.WARN, TAG, "usuario  no logueado " );
                }
            }
        };
    }

    public void showToolbar(boolean upButton){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @OnClick(R.id.btnJoin)
    public void createAccount(View view) {
        // TODO submit data to server...
        String email = edtUserMail.getText().toString();
        String password = edtPassword.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CreateAccountActivity.this, "Cuenta Creada Exitosamente", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(CreateAccountActivity.this, "Ocurrió un Error al crear la cuenta", Toast.LENGTH_SHORT).show();
                            FirebaseCrash.logcat(Log.ERROR, TAG, "Ocurrió un Error al crear la cuenta" );
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(mAuthListener);
    }
}
