package com.avrilhb.quinielaandroid.login.presenter;

import android.app.Activity;

import com.avrilhb.quinielaandroid.login.interactor.LoginInteractor;
import com.avrilhb.quinielaandroid.login.interactor.LoginInteractorImpl;
import com.avrilhb.quinielaandroid.login.view.LoginView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by avrilhb on 26/01/18.
 */

public class LoginPresenterImpl implements LoginPresenter{

    private LoginView loginView;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        interactor = new LoginInteractorImpl(this);
    }

    @Override
    public void signUp(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        loginView.disableInput();
        loginView.showProgressBar();
        interactor.signUp(username, password, activity, firebaseAuth);
    }

    @Override
    public void loginSuccess() {
        loginView.goMenu();
        loginView.hideProgressBar();
    }

    @Override
    public void loginError(String error) {
        loginView.enableInput();
        loginView.hideProgressBar();
        loginView.loginError(error);
    }
}
