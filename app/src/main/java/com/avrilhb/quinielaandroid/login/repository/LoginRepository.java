package com.avrilhb.quinielaandroid.login.repository;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by avrilhb on 30/04/18.
 */

public interface LoginRepository {

    void SignUp(String username,String password, Activity activity, FirebaseAuth firebaseAuth);
}
