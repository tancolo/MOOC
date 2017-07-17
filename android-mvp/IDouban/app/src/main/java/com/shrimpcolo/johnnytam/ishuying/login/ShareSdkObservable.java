package com.shrimpcolo.johnnytam.ishuying.login;


import android.support.annotation.NonNull;

import com.shrimpcolo.johnnytam.ishuying.beans.PlatformWrapper;

import cn.sharesdk.framework.Platform;
import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

public final class ShareSdkObservable {

    private static final String TAG = ShareSdkObservable.class.getSimpleName();

    public static Observable<PlatformWrapper> login(@NonNull Platform platform) {
        checkNotNull(platform, "platform == null");

        return Observable.create(new LoginEventOnSubscribe(platform));
    }


}
