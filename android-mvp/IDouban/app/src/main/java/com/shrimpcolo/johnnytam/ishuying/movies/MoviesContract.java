package com.shrimpcolo.johnnytam.ishuying.movies;

import com.shrimpcolo.johnnytam.ishuying.BasePresenter;
import com.shrimpcolo.johnnytam.ishuying.BaseView;
import com.shrimpcolo.johnnytam.ishuying.beans.Movie;

import java.util.List;


public interface MoviesContract {

    interface View extends BaseView<Presenter> {

        void showRefreshedMovies(List<Movie> movies);

        void showLoadedMoreMovies(List<Movie> movies);

        void showNoMovies();

        void showNoLoadedMoreMovies();

        void setRefreshedIndicator(boolean active);//indicator of SwipeRefreshLayout
    }

    interface Presenter extends BasePresenter {

        void loadRefreshedMovies(boolean forceUpdate);

        void loadMoreMovies(int movieStartIndex);

        void cancelRetrofitRequest();

        void unSubscribe();
    }
}
