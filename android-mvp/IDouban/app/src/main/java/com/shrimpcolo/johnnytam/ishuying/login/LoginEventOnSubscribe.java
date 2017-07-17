package com.shrimpcolo.johnnytam.ishuying.login;

import com.shrimpcolo.johnnytam.ishuying.beans.PlatformWrapper;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

public class LoginEventOnSubscribe implements Observable.OnSubscribe<PlatformWrapper> {

    final Platform platform;

    public LoginEventOnSubscribe(Platform platform) {
        this.platform = platform;
    }

    @Override
    public void call(Subscriber<? super PlatformWrapper> subscriber) {

        final PlatformActionListener platformActionListener = new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(new PlatformWrapper(platform, action, hashMap));
                }
            }

            @Override
            public void onError(Platform platform, int action, Throwable throwable) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onError(throwable);
                }
            }

            @Override
            public void onCancel(Platform platform, int action) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(new PlatformWrapper(platform, action, null));
                }
            }
        };

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                platform.setPlatformActionListener(null);
            }
        });

        platform.setPlatformActionListener(platformActionListener);
        //关闭SSO授权
        platform.SSOSetting(false);
        platform.showUser(null);

    }
}
