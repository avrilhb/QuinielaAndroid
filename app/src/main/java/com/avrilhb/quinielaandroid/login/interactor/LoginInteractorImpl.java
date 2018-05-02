package com.avrilhb.quinielaandroid.login.interactor;

import android.app.Activity;

import com.avrilhb.quinielaandroid.login.presenter.LoginPresenter;
import com.avrilhb.quinielaandroid.login.repository.LoginRepository;
import com.avrilhb.quinielaandroid.login.repository.LoginRepositoryImpl;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by avrilhb on 26/01/18.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private LoginPresenter presenter;
    private LoginRepository repository;

    public LoginInteractorImpl(LoginPresenter presenter) {
        this.presenter = presenter;
        repository = new LoginRepositoryImpl(presenter);
    }

    @Override
    public void signUp(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        repository.SignUp(username, password, activity, firebaseAuth);
    }
}
