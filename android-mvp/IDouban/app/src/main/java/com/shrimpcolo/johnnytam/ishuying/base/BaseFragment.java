package com.shrimpcolo.johnnytam.ishuying.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.shrimpcolo.johnnytam.ishuying.HomeActivity;
import com.shrimpcolo.johnnytam.ishuying.R;

import java.util.List;


/**
 * Created by Johnny Tam on 2017/3/24.
 */

public abstract class BaseFragment<T> extends Fragment {

    protected RecyclerView mRecyclerView;

    protected BaseRecycleViewAdapter mAdapter;

    protected List<T> mAdapterData;

    protected View mView;

    protected LinearLayoutManager mLayoutManager;

    protected FloatingActionButton mFab;


    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();
        initRecycleViewAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initRecycleView(inflater, container, savedInstanceState);

        initSwipeRefreshLayout();

        initEndlessScrollListener();

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initFab();
        startPresenter();
    }

    private void initFab() {
        HomeActivity homeActivity = (HomeActivity)getActivity();
        if (homeActivity != null ) {
            mFab = (FloatingActionButton) homeActivity.findViewById(R.id.fab);
        }
    }

    protected abstract void initVariables();

    protected abstract void initRecycleViewAdapter();

    protected abstract void initRecycleView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initSwipeRefreshLayout();

    protected abstract void initEndlessScrollListener();

    protected abstract void startPresenter();

}
