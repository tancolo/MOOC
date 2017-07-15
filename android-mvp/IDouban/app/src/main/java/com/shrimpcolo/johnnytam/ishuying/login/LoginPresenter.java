package com.shrimpcolo.johnnytam.ishuying.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.shrimpcolo.johnnytam.ishuying.api.IDoubanService;

import static com.google.common.base.Preconditions.checkNotNull;


public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private LoginContract.LoginView loginView;

    //private LoginContract.LoginDialogView loginDialogView;

    private IDoubanService loginService;

    public LoginPresenter(@NonNull IDoubanService loginService,
                          @NonNull LoginContract.LoginView loginView) {

        this.loginService = checkNotNull(loginService, "IDoubanService cannot be null!");
        this.loginView = checkNotNull(loginView, "login view cannot be null!");
        //this.loginDialogView = checkNotNull(loginDialog, "login Dialog view cannot be null!");

        this.loginView.setPresenter(this);
        //this.loginDialogView.setPresenter(this);
    }


    @Override
    public void start() {
        loadLoginData();
    }

    @Override
    public void loadLoginData() {
        Log.e(TAG, "===> loadLoginData()");
    }

}
