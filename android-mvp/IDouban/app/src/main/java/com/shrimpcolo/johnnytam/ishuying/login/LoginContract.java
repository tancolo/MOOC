package com.shrimpcolo.johnnytam.ishuying.login;

import com.shrimpcolo.johnnytam.ishuying.BasePresenter;
import com.shrimpcolo.johnnytam.ishuying.BaseView;
import com.shrimpcolo.johnnytam.ishuying.beans.UserInfo;

/**
 * Created by Johnny Tam on 2017/7/10.
 */

public interface LoginContract {

    interface LoginView extends BaseView<Presenter> {

        void showLoginSuccessView(boolean isShow);

        void showLoginView(boolean isShow);

        void setupPhoto(String imageUrl);

        void setupName(String name);

        void loginStatusChanged(boolean isLogin);

    }

    interface LoginDialogView extends BaseView<LoginDialogPresenter> {

    }

    interface Presenter extends BasePresenter {

        void loadLoginData();

        void doLogoutWithQQ();

        void updateLoginInfo(UserInfo userInfo);

        void updateLogoutInfo();

    }

    interface LoginDialogPresenter extends BasePresenter {
        void doLoginWithQQ();

        void unSubscribe();
    }

}
