package com.shrimpcolo.johnnytam.idouban.movies;

import android.support.annotation.NonNull;
import android.util.Log;

import com.shrimpcolo.johnnytam.idouban.HomeActivity;
import com.shrimpcolo.johnnytam.idouban.api.IDoubanService;
import com.shrimpcolo.johnnytam.idouban.beans.HotMoviesInfo;
import com.shrimpcolo.johnnytam.idouban.beans.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

public class MoviesPresenter implements MoviesContract.Presenter {

    private final static String TAG = MoviesPresenter.class.getSimpleName();

    private final MoviesContract.View mMoviesView;

    private final IDoubanService mIDuobanService;

    private boolean mFirstLoad = true;

    private int mMovieTotal;

    private Call<HotMoviesInfo> mMoviesRetrofitCallback;

    public MoviesPresenter(@NonNull IDoubanService moviesService, @NonNull MoviesContract.View moviesView) {
        mIDuobanService = checkNotNull(moviesService, "IDoubanServie cannot be null!");
        mMoviesView = checkNotNull(moviesView, "moviesView cannot be null!");

        Log.d(HomeActivity.TAG, TAG + ", MoviesPresenter: create!");
        mMoviesView.setPresenter(this);
    }

    @Override
    public void start() {
        loadRefreshedMovies(false);
    }

    @Override
    public void loadRefreshedMovies(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadMovies(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void loadMoreMovies(int movieStartIndex) {

        Log.e(HomeActivity.TAG, "movieStartIndex: " + movieStartIndex + ", mMovieTotal: " + mMovieTotal);
        if(movieStartIndex >= mMovieTotal) {
            processLoadMoreEmptyMovies();
            return;
        }

        mMoviesRetrofitCallback = mIDuobanService.searchHotMovies(movieStartIndex);
        mMoviesRetrofitCallback.enqueue(new Callback<HotMoviesInfo>() {
            @Override
            public void onResponse(Call<HotMoviesInfo> call, Response<HotMoviesInfo> response) {
                Log.d(HomeActivity.TAG, "===> onResponse: Thread.Id = " + Thread.currentThread().getId());
                List<Movie> moreMoviesList = response.body().getMovies();
                //debug
                Log.e(HomeActivity.TAG, "===> Response, size = " + moreMoviesList.size());

                processLoadMoreMovies(moreMoviesList);
            }

            @Override
            public void onFailure(Call<HotMoviesInfo> call, Throwable t) {
                Log.d(HomeActivity.TAG, "===> onFailure: Thread.Id = "
                        + Thread.currentThread().getId() + ", Error: " + t.getMessage());

                processLoadMoreEmptyMovies();
            }
        });
    }

    @Override
    public void cancelRetrofitRequest() {
        Log.e(HomeActivity.TAG, TAG + "=> cancelRetrofitRequest() isCanceled = " + mMoviesRetrofitCallback.isCanceled());
        if(!mMoviesRetrofitCallback.isCanceled()) mMoviesRetrofitCallback.cancel();
    }

    private void loadMovies(boolean forceUpdate, final boolean showLoadingUI){
        if(showLoadingUI){
            //MoviesFragment需要显示Loading 界面
            mMoviesView.setRefreshedIndicator(true);
        }

        if(forceUpdate) {
            mMoviesRetrofitCallback = mIDuobanService.searchHotMovies(0);
            mMoviesRetrofitCallback.enqueue(new Callback<HotMoviesInfo>() {
                @Override
                public void onResponse(Call<HotMoviesInfo> call, Response<HotMoviesInfo> response) {
                    Log.d(HomeActivity.TAG, "===> onResponse: Thread.Id = " + Thread.currentThread().getId());
                    List<Movie> moviesList = response.body().getMovies();

                    mMovieTotal = response.body().getTotal();
                    //debug
                    Log.e(HomeActivity.TAG, "===> Response, size = " + moviesList.size()
                            + " showLoadingUI: " + showLoadingUI + ", total = " + mMovieTotal);

                    //获取数据成功，Loading UI消失
                    if(showLoadingUI) {
                        mMoviesView.setRefreshedIndicator(false);
                    }

                    processMovies(moviesList);
                }

                @Override
                public void onFailure(Call<HotMoviesInfo> call, Throwable t) {
                    Log.d(HomeActivity.TAG, "===> onFailure: Thread.Id = "
                            + Thread.currentThread().getId() + ", Error: " + t.getMessage());

                    //获取数据成功，Loading UI消失
                    if(showLoadingUI) {
                        mMoviesView.setRefreshedIndicator(false);
                    }
                    processEmptyMovies();
                }
            });
        }

    }

    private void processMovies(List<Movie> movies) {
        if (movies.isEmpty()) {
            // Show a message indicating there are no movies for users
            processEmptyMovies();
        } else {
            // Show the list of movies
            mMoviesView.showRefreshedMovies(movies);
        }
    }

    private void processEmptyMovies() {
        //MoviesFragment需要给出提示
        mMoviesView.showNoMovies();
    }

    private void processLoadMoreMovies(List<Movie> movies) {
        if(movies.isEmpty()) {
            processLoadMoreEmptyMovies();
        }else {
            mMoviesView.showLoadedMoreMovies(movies);
        }
    }

    private void processLoadMoreEmptyMovies() {
        mMoviesView.showNoLoadedMoreMovies();
    }

}
