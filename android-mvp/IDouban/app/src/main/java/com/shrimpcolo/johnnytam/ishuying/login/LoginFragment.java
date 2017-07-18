package com.shrimpcolo.johnnytam.ishuying.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.shrimpcolo.johnnytam.ishuying.IShuYingApplication;
import com.shrimpcolo.johnnytam.ishuying.R;
import com.shrimpcolo.johnnytam.ishuying.api.DoubanManager;
import com.shrimpcolo.johnnytam.ishuying.utils.CircleTransformation;
import com.shrimpcolo.johnnytam.ishuying.utils.ConstContent;
import com.squareup.picasso.Picasso;

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
        loginDialogPresenter = new LoginDialogPresenter(DoubanManager.createDoubanService(), dialogView, presenter);
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
        //Log.e(TAG, "===> setPresenter, presenter = " + presenter);
    }

    @Override
    public void showLoginSuccessView(boolean isShow) {
        if (isShow) {
            loginSuccessContainer.setVisibility(View.VISIBLE);
        } else {
            loginSuccessContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoginView(boolean isShow) {
        if (isShow) {
            loginContainer.setVisibility(View.VISIBLE);
        } else {
            loginContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void setupPhoto(String imageUrl) {

        try {
            ImageView userPhoto = (ImageView) getView().findViewById(R.id.img_login_photo);

            int size = getResources().getDimensionPixelOffset(R.dimen.profile_aboutme_size);
            int width = getResources().getDimensionPixelOffset(R.dimen.profile_aboutme_border);
            int color = getResources().getColor(R.color.color_profile_photo_border);

            Picasso.with(IShuYingApplication.getInstance().getApplicationContext())
                    .load(imageUrl)
                    .resize(size, size)
                    .transform(new CircleTransformation(width, color))
                    .placeholder(R.mipmap.ic_ishuying)
                    .into(userPhoto);

        }catch (NullPointerException exp) {
            Log.e(TAG, exp.getMessage());
        }

    }

    @Override
    public void setupName(String name) {
        TextView userName = (TextView) getView().findViewById(R.id.txt_login_name);
        userName.setText(name);
    }

    @Override
    public void loginStatusChanged(boolean isLogin) {
        Intent intent = new Intent(ConstContent.ACTION_LOGIN_STATUS_CHANGED);
        intent.putExtra(ConstContent.INTENT_PARAM_IS_LOGIN, isLogin);

        LoginActivity loginActivity = (LoginActivity) getActivity();
        Log.e(TAG, "loginActivity = " + loginActivity);
        if (loginActivity != null && isLogin) {
            loginActivity.getLocalBroadcastManager().sendBroadcast(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        loginDialogPresenter.unSubscribe();
    }
}
