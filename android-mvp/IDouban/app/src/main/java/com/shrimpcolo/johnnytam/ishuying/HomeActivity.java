package com.shrimpcolo.johnnytam.ishuying;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cantrowitz.rxbroadcast.RxBroadcast;
import com.shrimpcolo.johnnytam.ishuying.aboutme.AboutFragment;
import com.shrimpcolo.johnnytam.ishuying.api.DoubanManager;
import com.shrimpcolo.johnnytam.ishuying.base.BaseActivity;
import com.shrimpcolo.johnnytam.ishuying.base.BaseViewPagerAdapter;
import com.shrimpcolo.johnnytam.ishuying.beans.UserInfo;
import com.shrimpcolo.johnnytam.ishuying.blogs.BlogContract;
import com.shrimpcolo.johnnytam.ishuying.blogs.BlogFragment;
import com.shrimpcolo.johnnytam.ishuying.blogs.BlogPresenter;
import com.shrimpcolo.johnnytam.ishuying.books.BooksContract;
import com.shrimpcolo.johnnytam.ishuying.books.BooksFragment;
import com.shrimpcolo.johnnytam.ishuying.books.BooksPresenter;
import com.shrimpcolo.johnnytam.ishuying.login.LoginActivity;
import com.shrimpcolo.johnnytam.ishuying.login.LoginListener;
import com.shrimpcolo.johnnytam.ishuying.login.LoginListenerObservable;
import com.shrimpcolo.johnnytam.ishuying.movies.MoviesContract;
import com.shrimpcolo.johnnytam.ishuying.movies.MoviesFragment;
import com.shrimpcolo.johnnytam.ishuying.movies.MoviesPresenter;
import com.shrimpcolo.johnnytam.ishuying.utils.CircleTransformation;
import com.shrimpcolo.johnnytam.ishuying.utils.ConstContent;
import com.shrimpcolo.johnnytam.ishuying.utils.FileUtils;
import com.shrimpcolo.johnnytam.ishuying.utils.Preferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HomeActivity extends BaseActivity implements LoginListenerObservable {

    public static final String TAG = HomeActivity.class.getSimpleName();

    private ViewPager mViewPager;

    private DrawerLayout mDrawerLayout;

    private NavigationView mNavigationView;

    private LinearLayout mLinearLayout;

    private ImageView mProfileView;

    private TextView mProfileName;

    private List<LoginListener> mLoginListenerList;

    private Subscription mSubscription;

    @Override
    protected void initVariables() {
        mLoginListenerList = new ArrayList<>();
        //debug activity task
        Log.i(TAG, "task id: " +  getTaskId());
        execAutoLoginProcess();
    }

    private void execAutoLoginProcess() {
        Log.e(TAG, "===> initVariables start ---- ");
        //获取缓存，并将数据保存到全局UserInfo中，用于自动登录
        Observable.just(IShuYingApplication.getInstance().getSharedPreferences().getBoolean(Preferences.PREFERENCE_AUTO_LOGIN, false))
                .filter(autoLogin -> autoLogin)
                .map(autoLogin -> FileUtils.recoverySerializableUser())
                .filter(userInfo -> userInfo != null)
                .doOnNext(userInfo -> IShuYingApplication.getInstance().setUser(userInfo))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo -> onLoginSuccess());//直接调用该方法 去通知所有相关类更改UI

        Log.e(TAG, "===> initVariables end ---- ");
    }

    /**
     * 用于测试rxjava的各种方法流程, 内容同方法 execAutoLoginProcess()
     */
    private void just4debugRxJavaProcess() {
        Log.e(TAG, "===> initVariables start ---- ");
        //获取缓存，并将数据保存到全局UserInfo中，用于自动登录
        Observable.just(IShuYingApplication.getInstance().getSharedPreferences().getBoolean(Preferences.PREFERENCE_AUTO_LOGIN, false))
                .filter(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean autoLogin) {
                        Log.d(TAG, "filter: autoLogin = " + autoLogin);
                        return autoLogin;
                    }
                })
                .map(new Func1<Boolean, UserInfo>() {
                    @Override
                    public UserInfo call(Boolean autoLogin) {
                        Log.d(TAG, "map: autoLogin = " + autoLogin);
                        return FileUtils.recoverySerializableUser();
                    }
                })
                .filter(new Func1<UserInfo, Boolean>() {
                    @Override
                    public Boolean call(UserInfo userInfo) {
                        Log.d(TAG, "filter2 : userInfo = " + userInfo);
                        return userInfo != null;
                    }
                })
                .doOnNext(new Action1<UserInfo>() {
                    @Override
                    public void call(UserInfo userInfo) {
                        IShuYingApplication.getInstance().setUser(userInfo);
                        Log.d(TAG, "doOnNext: setUser...");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ...");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ...");
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        Log.d(TAG, "onNext: ...");
                        onLoginSuccess();
                    }
                });

        Log.e(TAG, "===> initVariables end ---- ");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);

        initFAB();

        initDrawerLayout();

        initAndSetupNavigation();

        setupNavigationHeader();

        //Init Layout UI
        mViewPager = (ViewPager) findViewById(R.id.douban_view_pager);
        setupViewPager(mViewPager);

        initTabLayout();

        initOthersFragment();

        initLocalReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.e(TAG, "===> onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e(TAG, "===> onResume");
    }

    private void initFAB () {
        Log.e(TAG, "===> initFAB");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void initDrawerLayout() {
        Log.e(TAG, "===> initDrawerLayout");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerToggle.syncState();
        mDrawerLayout.addDrawerListener(drawerToggle);
    }

    private void initAndSetupNavigation() {
        Log.e(TAG, "===> initAndSetupNavigation");
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mLinearLayout = (LinearLayout) findViewById(R.id.tab_container);

        mNavigationView.setNavigationItemSelectedListener(item -> {

            boolean isClickLoginItem = false;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Log.e(TAG, "===> getFragments.size = " + getSupportFragmentManager().getFragments().size());

            //for show or hide fragment
            switch (item.getItemId()) {
                case R.id.navigation_item_movies:
                case R.id.navigation_item_book:
                    if (mLinearLayout.getVisibility() == View.GONE) {
                        mLinearLayout.setVisibility(View.VISIBLE);
                    }

                    getSupportFragmentManager().getFragments().forEach(fragment -> {
                        if (fragment.getTag().equals(ConstContent.MENU_BLOG) ||
                                fragment.getTag().equals(ConstContent.MENU_ABOUT)) {
                            transaction.hide(fragment);
                        } else {
                            transaction.show(fragment);
                        }
                    });

                    break;
                case R.id.navigation_item_blog:
                case R.id.navigation_item_about:
                    if (mLinearLayout.getVisibility() == View.VISIBLE) {
                        mLinearLayout.setVisibility(View.GONE);
                    }
                    break;

                default:
                    break;
            }

            //for all fragments
            switch (item.getItemId()) {

                case R.id.navigation_item_movies:
                    mViewPager.setCurrentItem(ConstContent.TAB_INDEX_MOVIES);

                    break;
                case R.id.navigation_item_book:
                    mViewPager.setCurrentItem(ConstContent.TAB_INDEX_BOOK);

                    break;
                case R.id.navigation_item_about:
                case R.id.navigation_item_blog:

                    getSupportFragmentManager().getFragments().forEach(fragment -> {
                        String fragmentTag = fragment.getTag();
                        if ((item.getItemId() == R.id.navigation_item_blog && fragmentTag.equals(ConstContent.MENU_BLOG))
                                || (item.getItemId() == R.id.navigation_item_about && fragmentTag.equals(ConstContent.MENU_ABOUT))) {
                            transaction.show(fragment);
                        } else {
                            transaction.hide(fragment);
                        }

                    });
                    break;

                case R.id.navigation_item_login:
                    isClickLoginItem = true;
                    //Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    //intent.putExtra(ConstContent.INTENT_PARAM_LOGIN_LOGOUT_TYPE, ConstContent.INTENT_PARAM_TYPE_LOGIN);
                    //startActivity(intent);
                    //startActivityForResult(intent, ConstContent.LOGIN_REQUEST_CODE);

                    break;
//                case R.id.navigation_item_logout://not used
//                    intent = new Intent(HomeActivity.this, LoginActivity.class);
//                    intent.putExtra(ConstContent.INTENT_PARAM_LOGIN_LOGOUT_TYPE, ConstContent.INTENT_PARAM_TYPE_LOGIN);
//                    startActivity(intent);
//                    break;
                default:
                    break;
            }

            item.setCheckable(true);
            transaction.commit();
            mDrawerLayout.closeDrawers();

            if (isClickLoginItem) {
                isClickLoginItem = false;
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            return true;
        });

    }

    private void setupNavigationHeader() {
        Log.e(TAG, "===> setupNavigationHeader");
        View headView = mNavigationView.inflateHeaderView(R.layout.navigation_header);
        mProfileView = (ImageView) headView.findViewById(R.id.img_profile_photo);
        mProfileName = (TextView) headView.findViewById(R.id.txt_profile_name);

        if (mProfileView != null) {
            mProfileView.setOnClickListener(view -> {
                Log.e(TAG, "===> onClick...!");

                //隐藏Movie， Book Fragments
                if (mLinearLayout.getVisibility() == View.VISIBLE) {
                    mLinearLayout.setVisibility(View.GONE);
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                getSupportFragmentManager().getFragments().forEach(fragment -> {
                    if (ConstContent.MENU_ABOUT.equals(fragment.getTag())) {
                        transaction.show(fragment);
                    } else {
                        transaction.hide(fragment);
                    }

                });

                transaction.commit();
                mDrawerLayout.closeDrawers();
                mNavigationView.getMenu().getItem(2).setChecked(true);
            });
        }

        updateHeaderViewProfile();
    }

    private void initOthersFragment() {
        Log.e(TAG, "===> initOthersFragment");
        //init blog fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BlogFragment jianshuFragment = BlogFragment.newInstance();
        AboutFragment aboutFragment = AboutFragment.newInstance();
        addListener(aboutFragment);//aboutFragment监听HomeActivity登录状态

        transaction.add(R.id.frame_container, jianshuFragment, ConstContent.MENU_BLOG);
        transaction.add(R.id.frame_container, aboutFragment, ConstContent.MENU_ABOUT);
        transaction.commit();

        createOtherPresenter(jianshuFragment);

    }
    private void createOtherPresenter(BlogContract.View blogFragment) {
        Log.d(TAG, "===> createOtherPresenter");
        new BlogPresenter(DoubanManager.createDoubanService(), blogFragment);
    }

    private void initTabLayout() {
        Log.d(TAG, "===> initTabLayout");
        TabLayout tabLayout = (TabLayout) findViewById(R.id.douban_sliding_tabs);

        if(tabLayout != null) {
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.setupWithViewPager(mViewPager);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        Log.d(TAG, "===> setupViewPager");
        HomePagerAdapter pagerAdapter = new HomePagerAdapter(getSupportFragmentManager());

        MoviesFragment moviesFragment = MoviesFragment.newInstance();
        BooksFragment booksFragment = BooksFragment.newInstance();
        //Log.e(TAG, "setupViewPager, moviesFragment = " + moviesFragment);
        //Log.e(TAG, "setupViewPager, booksFragment = " + booksFragment);

        pagerAdapter.addFragment(moviesFragment, getApplicationContext().getResources().getString(R.string.tab_movies_fragment));
        pagerAdapter.addFragment(booksFragment, getApplicationContext().getResources().getString(R.string.tab_books_fragment));

        viewPager.setAdapter(pagerAdapter);

        createPresenter(moviesFragment, booksFragment);
    }

    private void createPresenter(MoviesContract.View moviesFragment, BooksContract.View booksFragment) {
        Log.i(TAG, " createPresenter, fragmentView = " + moviesFragment);
        Log.i(TAG, " createPresenter, booksFragment = " + booksFragment);

        //Create the movies presenter
        MoviesPresenter moviesPresenter = new MoviesPresenter(DoubanManager.createDoubanService(), moviesFragment);
        BooksPresenter booksPresenter = new BooksPresenter(DoubanManager.createDoubanService(), booksFragment);
    }

    private void initLocalReceiver() {
        Log.d(TAG, "===> initLocalReceiver");
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstContent.ACTION_LOGIN_STATUS_CHANGED);

        mSubscription = RxBroadcast.fromLocalBroadcast(this, filter)
                .filter(intent -> ConstContent.ACTION_LOGIN_STATUS_CHANGED.equals(intent.getAction()))
                .map(intent -> intent.getBooleanExtra(ConstContent.INTENT_PARAM_IS_LOGIN, false))
                .subscribe(isLogin -> {
                    Log.e(TAG, "isLogin； " + isLogin);
                    if (isLogin) {
                        onLoginSuccess();
                    }else {
                        onLogoutSuccess();
                    }
                });

    }

    @Override
    public void addListener(LoginListener listener) {
        mLoginListenerList.add(listener);
    }

    @Override
    public void removeListener(LoginListener listener) {
        mLoginListenerList.remove(listener);
    }

    @Override
    public void onLoginSuccess() {

        //update Navigation head view, photo & name
        updateHeaderViewProfile();

        //notify other listeners
        Observable.from(mLoginListenerList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(LoginListener::onLoginSuccess);
    }

    @Override
    public void onLogoutSuccess() {

        //update Navigation head view, photo & name to default status.
        updateHeaderViewProfile();

        Observable.from(mLoginListenerList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(LoginListener::onLogoutSuccess);
    }

    private static class HomePagerAdapter extends BaseViewPagerAdapter {

        public HomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 通过UserInfo判断是否是登录状态，做不同UI显示
     */
    private void updateHeaderViewProfile() {

        //userInfo maybe null, that'ok
        UserInfo userInfo = IShuYingApplication.getInstance().getUser();
        Log.e(TAG, "===> userInfo : " + userInfo);

        int size = getResources().getDimensionPixelOffset(R.dimen.profile_avatar_size);
        int width = getResources().getDimensionPixelOffset(R.dimen.profile_avatar_border);
        int color = getResources().getColor(R.color.color_profile_photo_border);

        if (userInfo != null) {//Login status
            Picasso.with(this)
                    .load(userInfo.getUserIcon())
                    .resize(size, size)
                    .transform(new CircleTransformation(width, color))
                    .placeholder(R.mipmap.ic_ishuying)
                    .into(mProfileView);

            mProfileName.setText(userInfo.getUserName());

        } else { //Logout status ==> default status
            Picasso.with(this)
                    .load(R.mipmap.dayuhaitang)
                    .resize(size, size)
                    .transform(new CircleTransformation(width, color))
                    .into(mProfileView);

            mProfileName.setText(R.string.author_name);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "===> onDestroy: isUnsubscribed -> " + mSubscription.isUnsubscribed());
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}
