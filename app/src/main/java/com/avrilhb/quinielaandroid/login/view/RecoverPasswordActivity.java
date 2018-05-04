package com.avrilhb.quinielaandroid.login.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecoverPasswordActivity extends AppCompatActivity {

    @BindView(R.id.txtTitleTool) TextView txtTitleTool;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.txtRecoverPassword) EditText txtPassword;
    @BindView(R.id.btnRecoverPassword) Button btnRecoverPassword;

    private FirebaseAuth firebaseAuth;
    private static final String TAG = "RecoverPasswordActivity";
    private String email;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private boolean validaData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        ButterKnife.bind(this);

        showToolbar(true);
        txtTitleTool.setText(getResources().getString(R.string.btnRecoverLogin));

        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void showToolbar(boolean upButton){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

    @OnClick(R.id.btnRecoverPassword)
    public void recoverPassword(View view) {
        email = txtPassword.getText().toString();

        validateData(email);

        if(validaData){
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RecoverPasswordActivity.this, "Correo enviado exitosamente", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Email sent.");
                            }else{
                                Toast.makeText(RecoverPasswordActivity.this, "Ocurrió un error al recuperar la contraseña", Toast.LENGTH_SHORT).show();
                                FirebaseCrash.logcat(Log.ERROR, TAG, "Ocurrió un Error al recuperar password" );

                            }
                        }
                    });
        }
    }

    private void validateData(String email) {
        if(!email.equals("")) {
            if(!email.matches(emailPattern)){
                Toast.makeText(this, "Por favor introduce un email válido", Toast.LENGTH_SHORT).show();
            }else{
                validaData = true;
            }
        }else{
            validaData = false;
            Toast.makeText(this, "El campo no puede estar vacío", Toast.LENGTH_SHORT).show();
        }
    }
}
