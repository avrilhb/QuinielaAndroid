package com.avrilhb.quinielaandroid.login.interactor;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by avrilhb on 26/01/18.
 */

public interface LoginInteractor {

    void signUp(String username, String password, Activity activity, FirebaseAuth firebaseAuth);
}
