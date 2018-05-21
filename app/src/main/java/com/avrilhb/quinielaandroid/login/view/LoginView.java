package com.avrilhb.quinielaandroid.login.view;

import android.view.View;

/**
 * Created by avrilhb on 30/04/18.
 */

public interface LoginView {

    void createAccount(View view);
    void recoverPassword(View view);
    void showProgressBar();
    void hideProgressBar();
    void loginError(String error);
    void enableInput();
    void disableInput();
    void goMenu();
    void signUp(String email, String password);
}
