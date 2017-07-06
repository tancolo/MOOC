package com.shrimpcolo.johnnytam.idouban.blogs;

import com.shrimpcolo.johnnytam.idouban.BasePresenter;
import com.shrimpcolo.johnnytam.idouban.BaseView;
import com.shrimpcolo.johnnytam.idouban.beans.Blog;

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
