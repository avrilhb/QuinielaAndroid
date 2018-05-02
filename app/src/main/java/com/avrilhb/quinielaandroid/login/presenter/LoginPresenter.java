package com.avrilhb.quinielaandroid.login.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by avrilhb on 26/01/18.
 */

public interface LoginPresenter {

    void signUp(String username, String password, Activity activity, FirebaseAuth firebaseAuth); //Interactor
    void loginSuccess();
    void loginError(String error);
}
