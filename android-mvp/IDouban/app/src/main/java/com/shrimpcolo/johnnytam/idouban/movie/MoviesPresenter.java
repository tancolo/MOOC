package com.shrimpcolo.johnnytam.idouban.movie;

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

    public MoviesPresenter(@NonNull IDoubanService moviesService, @NonNull MoviesContract.View moviesView) {
        mIDuobanService = checkNotNull(moviesService, "IDoubanServie cannot be null!");
        mMoviesView = checkNotNull(moviesView, "moviesView cannot be null!");

        Log.d(HomeActivity.TAG, TAG + ", MoviesPresenter: create!");
        mMoviesView.setPresenter(this);
    }

    @Override
    public void loadMovies(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadMovies(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void start() {
        loadMovies(false);
    }

    private void loadMovies(boolean forceUpdate, final boolean showLoadingUI){
        if(showLoadingUI){
            //MoviesFragment需要显示Loading 界面
            mMoviesView.setLoadingIndicator(true);
        }

        if(forceUpdate){
            mIDuobanService.searchHotMovies().enqueue(new Callback<HotMoviesInfo>() {
                @Override
                public void onResponse(Call<HotMoviesInfo> call, Response<HotMoviesInfo> response) {
                    Log.d(HomeActivity.TAG, "===> onResponse: Thread.Id = " + Thread.currentThread().getId());
                    List<Movie> moviesList = response.body().getMovies();
                    //debug
                    Log.e(HomeActivity.TAG, "===> Response, size = " + moviesList.size()
                            + " showLoadingUI: " + showLoadingUI);

                    //获取数据成功，Loading UI消失
                    if(showLoadingUI) {
                        mMoviesView.setLoadingIndicator(false);
                    }

                    processMovies(moviesList);
                }

                @Override
                public void onFailure(Call<HotMoviesInfo> call, Throwable t) {
                    Log.d(HomeActivity.TAG, "===> onFailure: Thread.Id = "
                            + Thread.currentThread().getId() + ", Error: " + t.getMessage());

                    //获取数据成功，Loading UI消失
                    if(showLoadingUI) {
                        mMoviesView.setLoadingIndicator(false);
                    }
                    //mMoviesView.showLoadingMoviesError();
                }
            });
        }

    }

    private void processMovies(List<Movie> movies) {
        if (movies.isEmpty()) {
            // Show a message indicating there are no movies for users
            processEmptyTasks();
        } else {
            // Show the list of movies
            mMoviesView.showMovies(movies);
        }
    }

    private void processEmptyTasks() {
        //MoviesFragment需要给出提示
        mMoviesView.showNoMovies();
    }

}
