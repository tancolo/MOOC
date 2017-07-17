package com.shrimpcolo.johnnytam.ishuying.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.shrimpcolo.johnnytam.ishuying.R;
import com.shrimpcolo.johnnytam.ishuying.api.DoubanManager;
import com.shrimpcolo.johnnytam.ishuying.utils.ConstContent;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginContract.LoginView {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private LoginContract.Presenter presenter;

    private RelativeLayout loginContainer;

    private RelativeLayout loginSuccessContainer;

    private LoginDialogFragment loginDialogFragment;

    private LoginDialogPresenter loginDialogPresenter;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginContainer = (RelativeLayout) view.findViewById(R.id.rl_layout_login_container);
        loginSuccessContainer = (RelativeLayout) view.findViewById(R.id.rl_layout_login_success_container);

        //Rxbinding for login button
        RxView.clicks(view.findViewById(R.id.btn_login))
                .subscribe(aVoid -> showDialogFragment(true));

        loginDialogFragment = LoginDialogFragment.newInstance();
        createPresenter(loginDialogFragment);

        return view;
    }

    private void showDialogFragment(boolean isShow) {

        Log.e(TAG, "fragment size: " + getActivity().getSupportFragmentManager().getFragments().size());
        if (loginDialogFragment == null) {
            loginDialogFragment = LoginDialogFragment.newInstance();
        }

        if (isShow) {
            loginDialogFragment.show(getActivity().getSupportFragmentManager(), ConstContent.FRAGMENT_LOGIN_DIALOG_TAG);
        }else {
            loginDialogFragment.dismiss();
        }
    }

    private void createPresenter(LoginContract.LoginDialogView dialogView) {
        loginDialogPresenter = new LoginDialogPresenter(DoubanManager.createDoubanService(), dialogView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (presenter != null) {
            presenter.start();
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
        Log.e(TAG, "===> setPresenter, presenter = " + presenter);
    }

    @Override
    public void setupPhoto(String imageUrl) {

    }

    @Override
    public void setupName(String name) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        loginDialogPresenter.unSubscribe();
    }
}
