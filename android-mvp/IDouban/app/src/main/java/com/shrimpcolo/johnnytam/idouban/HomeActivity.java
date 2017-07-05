package com.shrimpcolo.johnnytam.idouban;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

import com.shrimpcolo.johnnytam.idouban.aboutme.AboutFragment;
import com.shrimpcolo.johnnytam.idouban.api.DoubanManager;
import com.shrimpcolo.johnnytam.idouban.base.BaseActivity;
import com.shrimpcolo.johnnytam.idouban.base.BaseViewPagerAdapter;
import com.shrimpcolo.johnnytam.idouban.blogs.BlogFragment;
import com.shrimpcolo.johnnytam.idouban.books.BooksContract;
import com.shrimpcolo.johnnytam.idouban.books.BooksFragment;
import com.shrimpcolo.johnnytam.idouban.books.BooksPresenter;
import com.shrimpcolo.johnnytam.idouban.movies.MoviesContract;
import com.shrimpcolo.johnnytam.idouban.movies.MoviesFragment;
import com.shrimpcolo.johnnytam.idouban.movies.MoviesPresenter;
import com.shrimpcolo.johnnytam.idouban.utils.CircleTransformation;
import com.shrimpcolo.johnnytam.idouban.utils.ConstContent;
import com.squareup.picasso.Picasso;

public class HomeActivity extends BaseActivity {

    public static final String TAG = HomeActivity.class.getSimpleName();

    private ViewPager mViewPager;

    private DrawerLayout mDrawerLayout;

    private NavigationView mNavigationView;

    private LinearLayout mLinearLayout;

    @Override
    protected void initVariables() {

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
    }

    private void initFAB () {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

    }

    private void initDrawerLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerToggle.syncState();
        mDrawerLayout.addDrawerListener(drawerToggle);
    }

    private void initAndSetupNavigation() {
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mLinearLayout = (LinearLayout) findViewById(R.id.tab_container);

        mNavigationView.setNavigationItemSelectedListener(item -> {

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

            //for the all fragments
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

                    break;
                case R.id.navigation_item_logout:

                    break;
                default:
                    break;
            }

            transaction.commit();
            item.setCheckable(true);
            mDrawerLayout.closeDrawers();

            return true;
        });

    }

    private void setupNavigationHeader() {
        View headView = mNavigationView.inflateHeaderView(R.layout.navigation_header);
        ImageView profileView = (ImageView) headView.findViewById(R.id.img_profile_photo);
        TextView profileName = (TextView) headView.findViewById(R.id.txt_profile_name);

        int size = getResources().getDimensionPixelOffset(R.dimen.profile_avatar_size);
        int width = getResources().getDimensionPixelOffset(R.dimen.profile_avatar_border);
        int color = getResources().getColor(R.color.color_profile_photo_border);

        Picasso.with(this)
                .load(R.mipmap.dayuhaitang)
                .resize(size, size)
                .transform(new CircleTransformation(width, color))
                .into(profileView);

        if (profileView != null) {
            profileView.setOnClickListener(view -> {
                Log.e(TAG, "===> onClick...!");
                mDrawerLayout.closeDrawers();
                mNavigationView.getMenu().getItem(0).setChecked(true);
            });
        }
    }

    private void initOthersFragment() {
        //init blog fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BlogFragment jianshuFragment = new BlogFragment();
        AboutFragment aboutFragment = new AboutFragment();

        transaction.add(R.id.frame_container, jianshuFragment, ConstContent.MENU_BLOG);
        transaction.add(R.id.frame_container, aboutFragment, ConstContent.MENU_ABOUT);
        transaction.commit();
    }

    private void initTabLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.douban_sliding_tabs);

        if(tabLayout != null) {
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.setupWithViewPager(mViewPager);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        HomePagerAdapter pagerAdapter = new HomePagerAdapter(getSupportFragmentManager());

        MoviesFragment moviesFragment = MoviesFragment.newInstance();
        BooksFragment booksFragment = BooksFragment.newInstance();
        Log.e(TAG, "setupViewPager, moviesFragment = " + moviesFragment);
        Log.e(TAG, "setupViewPager, booksFragment = " + booksFragment);

        pagerAdapter.addFragment(moviesFragment, getApplicationContext().getResources().getString(R.string.tab_movies_fragment));
        pagerAdapter.addFragment(booksFragment, getApplicationContext().getResources().getString(R.string.tab_books_fragment));

        viewPager.setAdapter(pagerAdapter);

        createPresenter(moviesFragment, booksFragment);
    }

    private void createPresenter(MoviesContract.View moviesFragment, BooksContract.View booksFragment) {
        Log.e(TAG, " createPresenter, fragmentView = " + moviesFragment);
        Log.e(TAG, " createPresenter, booksFragment = " + booksFragment);

        //Create the movies presenter
        MoviesPresenter moviesPresenter = new MoviesPresenter(DoubanManager.createDoubanService(), moviesFragment);
        BooksPresenter booksPresenter = new BooksPresenter(DoubanManager.createDoubanService(), booksFragment);
    }

    static class HomePagerAdapter extends BaseViewPagerAdapter {

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
}
