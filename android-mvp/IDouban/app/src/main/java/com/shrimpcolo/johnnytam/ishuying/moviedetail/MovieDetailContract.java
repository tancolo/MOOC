package com.shrimpcolo.johnnytam.ishuying.moviedetail;

import com.shrimpcolo.johnnytam.ishuying.BasePresenter;
import com.shrimpcolo.johnnytam.ishuying.BaseView;

public interface MovieDetailContract {

    interface View extends BaseView<Presenter> {

        void showCollapsingToolbarTitle(String title);

        void showPicassoImage(String largeImagePath);

        void setMovieInfoToFragment(String movieInfo);

        void setMovieAltToFragment(String movieAlt);


    }

    interface Presenter extends BasePresenter {

        void loadMovieInfo();

        void loadMovieAlt();
    }
}
