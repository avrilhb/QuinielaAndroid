package com.avrilhb.quinielaandroid.login.view;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avrilhb.quinielaandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class CreateAccountActivity extends AppCompatActivity {

    @BindView(R.id.txtTitleTool) TextView txtTitleTool;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.edtUserName) EditText edtUserName;
    @BindView(R.id.edtUserMail) EditText edtUserMail;
    @BindView(R.id.edtPassword) EditText edtPassword;
    @BindView(R.id.edtConfirmPassword) EditText edtConfirmPassword;
    @BindView(R.id.btnJoin) Button btnJoin;
    @BindView(R.id.teamSpinner) Spinner teamSpinner;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDataBase;
    private DatabaseReference mUsersReference;
    private String userName;
    private String password;
    private String confirmPassword;
    private String email;
    private String selectedTeam;
    private double totalPoints = 0;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String TAG = "CreateAccountActivity";
    private boolean validaData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ButterKnife.bind(this);

        showToolbar(true);
        loadSpinner();

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

        mFirebaseDataBase = FirebaseDatabase.getInstance();
        mUsersReference = mFirebaseDataBase.getReference().child("users");
    }

    public void showToolbar(boolean upButton){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    private void loadSpinner() {

        ArrayAdapter<String> spinnerArrayAdapter  = new ArrayAdapter<String>(this,
                R.layout.spinner_item,
                getResources().getStringArray(R.array.team_array));
        teamSpinner.setAdapter(spinnerArrayAdapter);

    }

    @OnClick(R.id.btnJoin)
    public void createAccount(View view) {
        // TODO submit data to server...
        email = edtUserMail.getText().toString();
        password = edtPassword.getText().toString();
        confirmPassword = edtConfirmPassword.getText().toString();
        userName = edtUserName.getText().toString();
        
        validateData(userName, email, password, confirmPassword);

        if(validaData){
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CreateAccountActivity.this, "Cuenta Creada Exitosamente", Toast.LENGTH_SHORT).show();
                            mUsersReference.push().setValue(userName, selectedTeam);
                        }else {
                            Toast.makeText(CreateAccountActivity.this, "Ocurrió un Error al crear la cuenta", Toast.LENGTH_SHORT).show();
                            FirebaseCrash.logcat(Log.ERROR, TAG, "Ocurrió un Error al crear la cuenta" );
                        }
                    }
                });
        }
    }

    private void validateData(String userName, String email, String password, String confirmPassword) {
        if(!userName.equals("") && !email.equals("") && !password.equals("")
                && !confirmPassword.equals("")){
            if(!email.matches(emailPattern)){
                Toast.makeText(this, "Por favor introduce un email válido", Toast.LENGTH_SHORT).show();
            }else if(!confirmPassword.matches(password)){
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }else{
                validaData = true;
            }
        }else{
            validaData = false;
            Toast.makeText(this, "Existen campos vacíos", Toast.LENGTH_SHORT).show();
        }
    }

    @OnItemSelected(R.id.teamSpinner)
    public void selectTeam(Spinner spinner, int position) {
        selectedTeam = spinner.getSelectedItem().toString();
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
