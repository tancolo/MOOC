package com.shrimpcolo.johnnytam.ishuying.login;

import com.shrimpcolo.johnnytam.ishuying.beans.PlatformWrapper;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


public class LoginEventObservable implements ObservableOnSubscribe<PlatformWrapper> {

    final Platform platform;

    public LoginEventObservable(Platform platform) {
        this.platform = platform;
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<PlatformWrapper> emitter) throws Exception {
        final PlatformActionListener platformActionListener = new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(new PlatformWrapper(platform, action, hashMap));
                }
            }

            @Override
            public void onError(Platform platform, int action, Throwable throwable) {
                if (!emitter.isDisposed()) {
                    emitter.onError(throwable);
                }
            }

            @Override
            public void onCancel(Platform platform, int action) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(new PlatformWrapper(platform, action, null));
                }
            }
        };


        platform.setPlatformActionListener(platformActionListener);
        //关闭SSO授权
        platform.SSOSetting(false);
        platform.showUser(null);
    }
}
