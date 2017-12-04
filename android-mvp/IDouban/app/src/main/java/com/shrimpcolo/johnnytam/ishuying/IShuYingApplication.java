package com.shrimpcolo.johnnytam.ishuying;

import android.content.SharedPreferences;
import android.util.Log;

import com.mob.MobApplication;
import com.shrimpcolo.johnnytam.ishuying.beans.UserInfo;
import com.shrimpcolo.johnnytam.ishuying.utils.FileUtils;
import com.shrimpcolo.johnnytam.ishuying.utils.Preferences;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IShuYingApplication extends MobApplication {

    public static final String TAG = IShuYingApplication.class.getSimpleName();

    private static IShuYingApplication instance = null;

    private UserInfo user;

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = IShuYingApplication.this;

    }

    public static IShuYingApplication getInstance() {
        return instance;
    }


    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;

        if (this.user != null) {
            FileUtils.saveSerializableUser(this.user);
        }
    }

    public SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(Preferences.APP_SHARED_PREFERENCE, MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    private void setUserFromStoreFile() { //not used
        //获取缓存，并将数据保存到全局UserInfo中，用于自动登录
        Observable.just(IShuYingApplication.getInstance().getSharedPreferences().getBoolean(Preferences.PREFERENCE_AUTO_LOGIN, false))
                .filter(autoLogin -> autoLogin)
                .map(autoLogin -> FileUtils.recoverySerializableUser())
                .filter(userInfo -> userInfo != null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo -> {
                    IShuYingApplication.getInstance().setUser(userInfo);
                    Log.e(TAG, "userInfo: " + userInfo);
                });
    }
}
