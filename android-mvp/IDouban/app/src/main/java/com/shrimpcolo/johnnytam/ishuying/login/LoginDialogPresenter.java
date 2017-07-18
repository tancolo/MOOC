package com.shrimpcolo.johnnytam.ishuying.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.shrimpcolo.johnnytam.ishuying.IShuYingApplication;
import com.shrimpcolo.johnnytam.ishuying.api.IDoubanService;
import com.shrimpcolo.johnnytam.ishuying.beans.PlatformWrapper;
import com.shrimpcolo.johnnytam.ishuying.beans.UserInfo;
import com.shrimpcolo.johnnytam.ishuying.utils.FileUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginDialogPresenter implements LoginContract.LoginDialogPresenter {

    private static final String TAG = LoginDialogPresenter.class.getSimpleName();

    private LoginContract.LoginDialogView loginDialogView;

    private IDoubanService loginService;

    private LoginContract.Presenter presenter;

    private CompositeSubscription compositeSubscription;

    public LoginDialogPresenter(@NonNull IDoubanService loginService,
                                @NonNull LoginContract.LoginDialogView loginDialog,
                                @NonNull LoginContract.Presenter presenter) {

        this.loginService = checkNotNull(loginService, "IDoubanService cannot be null!");
        this.loginDialogView = checkNotNull(loginDialog, "login Dialog view cannot be null!");
        this.presenter = checkNotNull(presenter, "presenter == null");

        this.loginDialogView.setPresenter(this);

        compositeSubscription = new CompositeSubscription();
    }


    @Override
    public void start() {

    }

    @Override
    public void doLoginWithQQ() {
        Platform qqPlatform = ShareSDK.getPlatform(QQ.NAME);

        compositeSubscription.add(ShareSdkObservable.login(qqPlatform)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PlatformWrapper>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, Log.getStackTraceString(e));
                    }

                    @Override
                    public void onNext(PlatformWrapper platformWrapper) {
                        if (Platform.ACTION_USER_INFOR == platformWrapper.getAction()) {
                            //Log.e(TAG, "====> onNext " + platformWrapper.getPlatform().getName() + ", Thread ID: " + Thread.currentThread().getId());
                            Log.e(TAG, "====> onNext hashmap: " + platformWrapper.getHashMap());

                            if (platformWrapper.getHashMap() != null) {
                                processLoginInfo(platformWrapper);
                            }
                        }
                    }
                }));



        //authorize(qqPlatform);
    }

    @Override
    public void unSubscribe() {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }

    //执行授权,获取用户信息
    //文档：http://wiki.mob.com/Android_%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E8%B5%84%E6%96%99
    private void authorize(Platform plat) {
        if (plat == null) {
            return;
        }

        //plat.setPlatformActionListener(this);
        //关闭SSO授权
        plat.SSOSetting(false);
        plat.showUser(null);

    }

    private void processLoginInfo(PlatformWrapper platformWrapper) {
        Platform platform = platformWrapper.getPlatform();
        HashMap<String, Object> hashMap = platformWrapper.getHashMap();

        //解析各种平台的数据，统一放到UserInfo中使用
        UserInfo userInfo = new UserInfo();

        Iterator ite = hashMap.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry entry = (Map.Entry) ite.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            //Log.e(TAG, " " + key + "： " + value);

            if (QQ.NAME.equals(platform.getName())) {
                if (key.equals("nickname")) {
                    userInfo.setUserName((String) value);

                } else if (key.equals("figureurl_qq_2")) {
                    userInfo.setUserIcon((String) value);

                } else if (key.equals("gender")) {
                    UserInfo.Gender gender = value.equals("男") ? UserInfo.Gender.MALE : UserInfo.Gender.FEMALE;
                    userInfo.setUserGender(gender);

                }
            } else if (platform.equals(SinaWeibo.NAME)) {
                if(key.equals("name")) {
                    userInfo.setUserName((String) value);

                }else if(key.equals("avatar_hd")) {
                    userInfo.setUserIcon((String) value);

                }else if(key.equals("gender")) {
                    UserInfo.Gender gender = value.equals("m") ? UserInfo.Gender.MALE : UserInfo.Gender.FEMALE;
                    userInfo.setUserGender(gender);
                }
            } else {//wechat 个人无法申请微信登录
            }
        }//end while

        //print QQ info
        Log.e(TAG, userInfo.toString());

        Log.e(TAG, "getInstance() " + IShuYingApplication.getInstance());
        IShuYingApplication.getInstance().setUser(userInfo);

        FileUtils.saveAutoLogin(true, 1);

        presenter.updateLoginInfo(userInfo);

        //send intent to HomeActivity
//        Intent intent = new Intent();
//        intent.putExtra("userInfo", userInfo);
//        ((LoginDialogFragment)loginDialogView).getActivity().setResult(Activity.RESULT_OK, intent);
    }

}
