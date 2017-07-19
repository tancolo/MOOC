package com.shrimpcolo.johnnytam.ishuying.login;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.shrimpcolo.johnnytam.ishuying.R;
import com.shrimpcolo.johnnytam.ishuying.api.DoubanManager;
import com.shrimpcolo.johnnytam.ishuying.base.BaseActivity;
import com.shrimpcolo.johnnytam.ishuying.utils.ConstContent;

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

//    private LoginFragment loginFragment;
//
//    private LoginDialogFragment loginDialogFragment;

    private LoginContract.Presenter presenter;

    @Override
    protected void initVariables() {
        Log.i(TAG, "task id: " + getTaskId());
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);

        setUpFragments();
    }

    private void setUpFragments() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        LoginFragment loginFragment = LoginFragment.newInstance();
//        LoginDialogFragment loginDialogFragment = LoginDialogFragment.newInstance();

        transaction.add(R.id.fl_login_container, loginFragment, ConstContent.FRAGMENT_LOGIN_TAG);
        //transaction.add(R.id.fl_login_container, loginDialogFragment, ConstContent.FRAGMENT_LOGIN_DIALOG_TAG);

        transaction.commit();

        createPresenter(loginFragment);
    }

    private void createPresenter(LoginContract.LoginView loginView) {
        Log.d(TAG, "createPresenter() ");
        new LoginPresenter(DoubanManager.createDoubanService(), loginView);
    }

}
