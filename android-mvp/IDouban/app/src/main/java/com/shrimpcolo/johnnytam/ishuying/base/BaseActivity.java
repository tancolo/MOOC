package com.shrimpcolo.johnnytam.ishuying.base;

import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

/**
 * The father of all activities in IDouban App
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected LocalBroadcastManager mLocalBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

        initVariables();
        initViews(savedInstanceState);

    }

    public LocalBroadcastManager getLocalBroadcastManager() {
        return mLocalBroadcastManager;
    }

    protected abstract void initVariables();

    protected abstract void initViews(Bundle savedInstanceState);
}
