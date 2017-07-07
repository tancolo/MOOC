package com.shrimpcolo.johnnytam.ishuying.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * The father of all activities in IDouban App
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();
        initViews(savedInstanceState);
    }

    protected abstract void initVariables();

    protected abstract void initViews(Bundle savedInstanceState);
}
