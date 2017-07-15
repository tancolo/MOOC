package com.shrimpcolo.johnnytam.ishuying.login;

import com.shrimpcolo.johnnytam.ishuying.BasePresenter;
import com.shrimpcolo.johnnytam.ishuying.BaseView;

/**
 * Created by Johnny Tam on 2017/7/10.
 */

public interface LoginContract {

    interface LoginView extends BaseView<Presenter> {

        void setupPhoto(String imageUrl);

        void setupName(String name);
    }

    interface LoginDialogView extends BaseView<LoginDialogPresenter> {

    }

    interface Presenter extends BasePresenter {

        void loadLoginData();

//        void popupLoginDialog();

    }

    interface  LoginDialogPresenter extends BasePresenter {
        void doLoginWithQQ();
    }

}
