package com.shrimpcolo.johnnytam.ishuying.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.shrimpcolo.johnnytam.ishuying.IShuYingApplication;
import com.shrimpcolo.johnnytam.ishuying.R;
import com.shrimpcolo.johnnytam.ishuying.api.IDoubanService;
import com.shrimpcolo.johnnytam.ishuying.beans.UserInfo;
import com.shrimpcolo.johnnytam.ishuying.utils.FileUtils;
import com.shrimpcolo.johnnytam.ishuying.utils.ToastUtils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

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

    @Override
    public void doLogoutWithQQ() {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);

        if (qq.isAuthValid()) {//删除了ShareSdk 获得的QQ授权的缓存 cn_sharesdk_weibodb_QQ_2.xml
            Log.d(TAG, "remove QQ account!");
            qq.removeAccount(true);

            processLogoutInfo();
        }
    }

    @Override
    public void updateLoginInfo(UserInfo userInfo) {

        loginView.showLoginSuccessView(true);

        loginView.showLoginView(false);

        loginView.setupPhoto(userInfo.getUserIcon());

        loginView.setupName(userInfo.getUserName());

        loginView.loginStatusChanged(true);
    }

    @Override
    public void updateLogoutInfo() {
        loginView.showLoginSuccessView(false);

        loginView.showLoginView(true);

        loginView.loginStatusChanged(false);
    }


    private void processLogoutInfo() {

        updateLogoutInfo();
        ToastUtils.showShortToast(R.string.view_set_logout_success);

        IShuYingApplication.getInstance().setUser(null);//全局UserInfo为null
        FileUtils.saveAutoLogin(false, 0);//取消自动登录
        FileUtils.clearSerializableUser(); //清除userInfo 文件


    }

}
