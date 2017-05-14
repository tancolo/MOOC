package com.shrimpcolo.johnnytam.idouban.movies;

import com.shrimpcolo.johnnytam.idouban.BasePresenter;
import com.shrimpcolo.johnnytam.idouban.BaseView;
import com.shrimpcolo.johnnytam.idouban.beans.Movie;

import java.util.List;


public interface MoviesContract {

    interface View extends BaseView<Presenter> {

        void showMovies(List<Movie> movies);

        void showNoMovies();

        void setLoadingIndicator(boolean active);
    }

    interface Presenter extends BasePresenter {
        void loadMovies(boolean forceUpdate);
    }
}
