package com.shrimpcolo.johnnytam.ishuying;

import android.content.SharedPreferences;

import com.mob.MobApplication;
import com.shrimpcolo.johnnytam.ishuying.beans.UserInfo;
import com.shrimpcolo.johnnytam.ishuying.utils.FileUtils;
import com.shrimpcolo.johnnytam.ishuying.utils.Preferences;


public class IShuYingApplication extends MobApplication {

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
}
