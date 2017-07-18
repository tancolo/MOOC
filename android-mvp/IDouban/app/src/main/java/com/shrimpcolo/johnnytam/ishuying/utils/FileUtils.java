package com.shrimpcolo.johnnytam.ishuying.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.shrimpcolo.johnnytam.ishuying.IShuYingApplication;
import com.shrimpcolo.johnnytam.ishuying.beans.UserInfo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileUtils {

    private FileUtils() {
    }

    /**
     * 检查sdcard是否存在
     *
     * @return
     */
    public static boolean checkSdCardExist() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 保存自动登录信息
     *
     * @param type
     */
    public static void saveAutoLogin(boolean isAutoLogin, int type) {
        SharedPreferences.Editor editor = IShuYingApplication.getInstance().getSharedPreferences().edit();
        editor.putBoolean(Preferences.PREFERENCE_AUTO_LOGIN, isAutoLogin);
        editor.putInt(Preferences.PREFERENCE_LOGIN_TYPE, type);
        editor.apply();
    }

    /**
     * 保存用户信息
     *
     * @param info
     */
    public static void saveSerializableUser(UserInfo info) {
        if (FileUtils.checkSdCardExist()) {
            try {
                FileOutputStream fos = IShuYingApplication.getInstance().openFileOutput(ConstContent.APP_USER_INFO_FILE, Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(info);
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 恢复用户信息
     *
     * @return
     */
    public static UserInfo recoverySerializableUser() {

        if (FileUtils.checkSdCardExist()) {
            try {
                FileInputStream fis = IShuYingApplication.getInstance().openFileInput(ConstContent.APP_USER_INFO_FILE);
                ObjectInputStream ois = new ObjectInputStream(fis);
                UserInfo info = (UserInfo) ois.readObject();
                ois.close();
                fis.close();
                return info;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
