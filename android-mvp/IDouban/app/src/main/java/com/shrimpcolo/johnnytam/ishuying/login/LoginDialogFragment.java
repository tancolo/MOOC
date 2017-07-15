package com.shrimpcolo.johnnytam.ishuying.login;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.shrimpcolo.johnnytam.ishuying.R;


public class LoginDialogFragment extends DialogFragment implements LoginContract.LoginDialogView {

    private static final String TAG = LoginDialogFragment.class.getSimpleName();

    private LoginContract.LoginDialogPresenter presenter;

    private TextView weiboBtn;

    private TextView wechatBtn;

    private TextView qqBtn;

    public LoginDialogFragment() {

    }

    public static LoginDialogFragment newInstance() {
        return new LoginDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "===> onCreate()...");
        //setStyle(android.app.DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Panel);
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().getAttributes().windowAnimations = R.style.login_window_anim;
        Log.e(TAG, "===> onCreateDialog(), dialog = " + dialog);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.e(TAG, "===> onCreateView()-> getDialog = " + getDialog());
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//crash
        View rootView = inflater.inflate(R.layout.fragment_login_dialog, container, false);

        ImageButton closeBtn = (ImageButton) rootView.findViewById(R.id.ib_login_off);
        weiboBtn = (TextView) rootView.findViewById(R.id.tv_login_weibo);
        wechatBtn = (TextView) rootView.findViewById(R.id.tv_login_wechat);
        qqBtn = (TextView) rootView.findViewById(R.id.tv_login_qq);

        RxView.clicks(closeBtn).subscribe(aVoid -> this.dismiss());

        doLoginWithQQ();

        return rootView;
    }

    private void doLoginWithQQ() {
        RxView.clicks(qqBtn)
                .subscribe(aVoid -> {
                    this.dismiss();
                    presenter.doLoginWithQQ();
                });
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.e(TAG, "===> onActivityCreated, presenter = " + presenter);
        if (presenter != null) {
            presenter.start();
        }
    }

    @Override
    public void setPresenter(LoginContract.LoginDialogPresenter presenter) {
        this.presenter = presenter;
        Log.e(TAG, "===> setPresenter, presenter = " + presenter);
    }
}
