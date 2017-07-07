package com.shrimpcolo.johnnytam.ishuying.blogs;

import com.shrimpcolo.johnnytam.ishuying.BasePresenter;
import com.shrimpcolo.johnnytam.ishuying.BaseView;
import com.shrimpcolo.johnnytam.ishuying.beans.Blog;

import java.util.List;

/**
 * Created by Johnny Tam on 2017/7/5.
 */

public interface BlogContract {

    interface View extends BaseView<Presenter> {

        void showRefreshedBlogs(List<Blog> blogs);

        void showLoadedMoreBlogs(List<Blog> blogs);

        void showNoBlogs();

        void showNoLoadedMoreBlogs();

        void setRefreshedIndicator(boolean active);//indicator of SwipeRefreshLayout
    }

    interface Presenter extends BasePresenter {

        void loadRefreshedBlogs(boolean forceUpdate);

        void loadMoreBlogs(int blogStartIndex);

        void unSubscribe();

    }
}
