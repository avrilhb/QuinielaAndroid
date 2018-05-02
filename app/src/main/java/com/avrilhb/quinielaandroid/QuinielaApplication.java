package com.avrilhb.quinielaandroid;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by avrilhb on 30/04/18.
 */

public class QuinielaApplication extends Application {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final String TAG = "QuinielaApplication";
    private FirebaseStorage firebaseStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseCrash.log("Inicializando variables en QuinielaApplication");

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
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

        firebaseStorage = FirebaseStorage.getInstance();
    }

    public StorageReference getStorageReference(){
        return firebaseStorage.getReference();
    }
}
