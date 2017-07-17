package com.shrimpcolo.johnnytam.ishuying.beans;


import java.util.HashMap;

import cn.sharesdk.framework.Platform;

public class PlatformWrapper {

    private Platform platform;

    private HashMap<String, Object> hashMap;

    private int action;

    public PlatformWrapper() {

    }

    public PlatformWrapper(Platform platform) {
        this.platform = platform;
        this.action = 0;
        this.hashMap = null;
    }

    public PlatformWrapper(Platform platform, int action, HashMap<String, Object> hashMap) {
        this.platform = platform;
        this.action = action;
        this.hashMap = hashMap;
    }

    public Platform getPlatform() {
        return platform;
    }

    public HashMap<String, Object> getHashMap() {
        return hashMap;
    }

    public int getAction() {
        return action;
    }
}
