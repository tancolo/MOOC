package com.shrimpcolo.johnnytam.idouban.moviedetail;

import android.app.Activity;
import android.content.res.Resources;

import com.shrimpcolo.johnnytam.idouban.R;
import com.shrimpcolo.johnnytam.idouban.beans.Movie;


public class MovieDetailPresenter implements MovieDetailContract.Presenter{

    private static final String TAG = MovieDetailPresenter.class.getSimpleName();

    private Movie mMovie;

    private MovieDetailContract.View mMovieDetailView;

    public MovieDetailPresenter(Movie movie, MovieDetailContract.View movieDetailView) {

        this.mMovie = movie;
        this.mMovieDetailView = movieDetailView;

        this.mMovieDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        showMovieDetail();
    }

    private void showMovieDetail(){
        mMovieDetailView.showCollapsingToolbarTitle(mMovie.getTitle());

        mMovieDetailView.showPicassoImage(mMovie.getImages().getLarge());
    }

    @Override
    public void loadMovieInfo() {
        //拼接影片信息, 导演， 主演，又名， 上映时间， 类型， 片长，地区， 语言，IMDB
        StringBuilder movieBuilder = new StringBuilder();
        Resources resources = ((Activity)mMovieDetailView).getResources();

        movieBuilder.append(resources.getString(R.string.movie_directors));
        for (Movie.DirectorsBean director : mMovie.getDirectors()) {
            movieBuilder.append(director.getName());
            movieBuilder.append(" ");
        }
        movieBuilder.append("\n");

        //主演
        movieBuilder.append(resources.getString(R.string.movie_casts));
        for (Movie.CastsBean cast : mMovie.getCasts()) {
            movieBuilder.append(cast.getName());
            movieBuilder.append(" ");
        }
        movieBuilder.append("\n");

        //又名
        movieBuilder.append(resources.getString(R.string.movie_aka));
        movieBuilder.append(mMovie.getOriginal_title());
        movieBuilder.append("\n");

        movieBuilder.append(resources.getString(R.string.movie_year));
        movieBuilder.append(mMovie.getYear());
        movieBuilder.append("\n");

        movieBuilder.append(resources.getString(R.string.movie_genres));
        for (int index = 0; index < mMovie.getGenres().size(); index++) {
            movieBuilder.append(mMovie.getGenres().get(index));
            movieBuilder.append(" / ");
        }
        movieBuilder.append("\n");

        movieBuilder.append(resources.getString(R.string.movie_during));
        movieBuilder.append(resources.getString(R.string.movie_not_find));
        movieBuilder.append("\n");

        movieBuilder.append(resources.getString(R.string.movie_countries));
        movieBuilder.append(resources.getString(R.string.movie_not_find));
        movieBuilder.append("\n");

        movieBuilder.append(resources.getString(R.string.movie_languages));
        movieBuilder.append(resources.getString(R.string.movie_not_find));
        movieBuilder.append("\n");

        movieBuilder.append(resources.getString(R.string.movie_imdb));
        movieBuilder.append(resources.getString(R.string.movie_not_find));
        movieBuilder.append("\n");

//        return movieBuilder.toString();

        mMovieDetailView.setMovieInfoToFragment(movieBuilder.toString());
    }

    @Override
    public void loadMovieAlt() {
        mMovieDetailView.setMovieAltToFragment(mMovie.getAlt());
    }
}
