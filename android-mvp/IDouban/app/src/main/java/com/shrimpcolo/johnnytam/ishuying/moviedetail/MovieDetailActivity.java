package com.shrimpcolo.johnnytam.ishuying.moviedetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.shrimpcolo.johnnytam.ishuying.R;
import com.shrimpcolo.johnnytam.ishuying.base.BaseActivity;
import com.shrimpcolo.johnnytam.ishuying.base.BaseViewPagerAdapter;
import com.shrimpcolo.johnnytam.ishuying.beans.Movie;
import com.shrimpcolo.johnnytam.ishuying.utils.ConstContent;
import com.squareup.picasso.Picasso;

import cn.sharesdk.onekeyshare.OnekeyShare;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 这里不应该使用MVP， 因为Movie数据是从电影列表Item 传入。
 * 但是，为了深入理解MVP，本类还是使用MVP模式
 * MovieDetailActivity  作为 View， {@link MovieDetailPresenter} 作为 Presenter
 * 目的是 让Activity 不接触到 Movie数据， 界面需要数据的显示 全部都从MovieDetailPresenter中处理
 * 动画效果参数说明： http://www.jianshu.com/p/c4a8bfbe1f40
 */
public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private MovieDetailContract.Presenter mPresenter;

    private String mMovieInfo = null;
    private String mMovieAlt = null;

    @Override
    protected void initVariables() {
        new MovieDetailPresenter((Movie) getIntent().getSerializableExtra(ConstContent.INTENT_EXTRA_MOVIE), this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_movie_detail);

        initToolbar();
        initFab();

        //setup view pager
        ViewPager viewPager = (ViewPager) findViewById(R.id.movie_viewpager);
        setupViewPager(viewPager);

        initTabLayout(viewPager);
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.movie_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        toolbar.inflateMenu(R.menu.menu_movie_detail);
//        toolbar.setOnMenuItemClickListener(item -> {
//
//            switch (item.getItemId()) {
//                case R.id.menu_action_share:
//                    Log.d(TAG, "Share the movie info!");
//                    showShare();
//                    break;
//                default :
//                    break;
//            }
//
//            return true;
//        });
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_movie_detail);
        fab.setOnClickListener(view -> showShare());
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        //oks.setTitle("标题"); // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitleUrl(mMovieAlt); // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setText("我是分享文本"); // text是分享文本，所有平台都需要这个字段
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片

        //oks.setUrl("http://sharesdk.cn"); // url仅在微信（包括好友和朋友圈）中使用
        //oks.setComment("我是测试评论文本"); // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setSite(getString(R.string.app_name)); // site是分享此内容的网站名称，仅在QQ空间使用
        //oks.setSiteUrl("http://sharesdk.cn"); // siteUrl是分享此内容的网站地址，仅在QQ空间使用

        oks.show(this); // 启动分享GUI
    }

    private void initTabLayout(ViewPager viewPager) {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.movie_sliding_tabs);
        if (tabLayout != null) {
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();

    }

    private void setupViewPager(ViewPager viewPager) {
        /* 通过Presenter获取Movies相关的Alt，以及MovieInfo， 然后将得到的信息回传到当前Activity中 mMovieInfo， mMovieAlt
            用于创建MovieDetailFragment。
            老实说，这是很扯的方法， 绕了一圈子，就是不让Activity， MovieDetailFragment直接接触Movie数据。
            这里，为了套MVP模式，暂且这么弄。
         */
        mPresenter.loadMovieAlt();
        mPresenter.loadMovieInfo();

//        Log.e(HomeActivity.TAG, "\n\n mMovieInfo = " + mMovieInfo + ", mMovieAlt = " + mMovieAlt);
        MovieDetailPagerAdapter adapter = new MovieDetailPagerAdapter(getSupportFragmentManager());
        MovieDetailFragment movieInfoFragment = MovieDetailFragment.createInstance(mMovieInfo, ConstContent.TYPE_MOVIE_INFO);
        MovieDetailFragment movieWebsiteFragment = MovieDetailFragment.createInstance(mMovieAlt, ConstContent.TYPE_MOVIE_WEBSITE);

        adapter.addFragment(movieInfoFragment, getString(R.string.movie_info));
        adapter.addFragment(movieWebsiteFragment, getString(R.string.movie_description));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void showCollapsingToolbarTitle(String title) {
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.movie_collapsing_toolbar);
        collapsingToolbar.setTitle(title);
    }

    @Override
    public void showPicassoImage(String largeImagePath) {
        ImageView movieImage = (ImageView) findViewById(R.id.movie_image);
        Picasso.with(movieImage.getContext())
               .load(largeImagePath)
               .into(movieImage);
    }

    @Override
    public void setMovieInfoToFragment(String movieInfo) {
        mMovieInfo = movieInfo;
    }

    @Override
    public void setMovieAltToFragment(String movieAlt) {
        mMovieAlt = movieAlt;
        Log.d(TAG, "movieAlt: " + movieAlt);
    }

    @Override
    public void setPresenter(@NonNull MovieDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    //For MoviePageAdapter
    static class MovieDetailPagerAdapter extends BaseViewPagerAdapter {

        public MovieDetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }
    }
}
