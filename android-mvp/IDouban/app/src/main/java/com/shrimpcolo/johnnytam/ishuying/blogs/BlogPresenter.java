package com.shrimpcolo.johnnytam.ishuying.blogs;

import android.util.Log;

import com.shrimpcolo.johnnytam.ishuying.HomeActivity;
import com.shrimpcolo.johnnytam.ishuying.api.IDoubanService;
import com.shrimpcolo.johnnytam.ishuying.beans.Blog;
import com.shrimpcolo.johnnytam.ishuying.beans.BlogsInfo;
import com.shrimpcolo.johnnytam.ishuying.utils.ConstContent;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Johnny Tam on 2017/7/5.
 */

public class BlogPresenter implements BlogContract.Presenter {

    private static final String TAG = BlogPresenter.class.getSimpleName();

    private BlogContract.View mBlogsView;

    private IDoubanService mBlogsService;

    private boolean mFirstLoad = true;

    private CompositeDisposable compositeDisposable;

    public BlogPresenter(@NonNull IDoubanService blogsService, @NonNull BlogContract.View blogsView) {
        Log.e(HomeActivity.TAG, TAG + "===> BlogPresenter");
        mBlogsService = checkNotNull(blogsService, "BlogsService cannot be null!");
        mBlogsView = checkNotNull(blogsView, "BlogsView cannot be null!");

        mBlogsView.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void start() {
        loadRefreshedBlogs(false);
    }

    @Override
    public void loadRefreshedBlogs(boolean forceUpdate) {
        loadBlogs(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadBlogs(boolean forceUpdate, final boolean showLoadingUI) {
        Log.e(TAG, "===> loadBlogs.  forceUpdate: " + forceUpdate + ", showLoadingUI: " + showLoadingUI + "\n");
        //+ Log.getStackTraceString(new Throwable()));

        if (forceUpdate) {
            mBlogsService.getBlogWithRxJava(ConstContent.API_BLOG_WEBSITE)
                    .doOnSubscribe(disposable -> {
                        if (showLoadingUI) {
                            mBlogsView.setRefreshedIndicator(true);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BlogsInfo>() {

                        @Override
                        public void onSubscribe(@NonNull Disposable disposable) {
                            compositeDisposable.add(disposable);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(HomeActivity.TAG, TAG + "===> onError: " + e.getMessage());

                            //获取数据成功，Loading UI消失
                            if (showLoadingUI) {
                                mBlogsView.setRefreshedIndicator(false);
                            }
                            processEmptyBlogs();
                        }

                        @Override
                        public void onComplete() {
                            Log.d(HomeActivity.TAG, TAG + "===> onCompleted() " + " showLoadingUI: " + showLoadingUI);
                            //获取数据成功，Loading UI消失
                            if (showLoadingUI) {
                                mBlogsView.setRefreshedIndicator(false);
                            }
                        }

                        @Override
                        public void onNext(BlogsInfo blogsInfo) {
                            List<Blog> blogList = blogsInfo.getBlog();
                            Log.d(HomeActivity.TAG, TAG + "===> onNext " + ", blogList size: " + blogList.size());

                            //debug
                            //Log.e(HomeActivity.TAG, TAG + "===> BlogsInfo, size = " + blogList.size());

                            processBlogs(blogList);
                        }
                    });
        }
    }

    @Override
    public void loadMoreBlogs(int blogStartIndex) {

    }

    @Override
    public void unSubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    private void processBlogs(List<Blog> blogs) {
        if (blogs.isEmpty()) {
            // Show a message indicating there are no blog for users
            processEmptyBlogs();
        } else {
            // Show the list of movies
            mBlogsView.showRefreshedBlogs(blogs);
        }
    }

    private void processEmptyBlogs() {
        //BlogsFragment需要给出提示
        mBlogsView.showNoBlogs();
    }


}
