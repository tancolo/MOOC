package com.shrimpcolo.johnnytam.idouban.movies;

import android.support.annotation.NonNull;
import android.util.Log;

import com.shrimpcolo.johnnytam.idouban.HomeActivity;
import com.shrimpcolo.johnnytam.idouban.api.IDoubanService;
import com.shrimpcolo.johnnytam.idouban.beans.HotMoviesInfo;
import com.shrimpcolo.johnnytam.idouban.beans.Movie;

import java.util.List;

import retrofit2.Call;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

public class MoviesPresenter implements MoviesContract.Presenter {

    private final static String TAG = MoviesPresenter.class.getSimpleName();

    private final MoviesContract.View mMoviesView;

    private final IDoubanService mIDuobanService;

    private boolean mFirstLoad = true;

    private int mMovieTotal;

    private Call<HotMoviesInfo> mMoviesRetrofitCallback;

    private Subscription mSubscription;

    private CompositeSubscription mCompositeSubscription;

    public MoviesPresenter(@NonNull IDoubanService moviesService, @NonNull MoviesContract.View moviesView) {
        mIDuobanService = checkNotNull(moviesService, "IDoubanServie cannot be null!");
        mMoviesView = checkNotNull(moviesView, "moviesView cannot be null!");

        Log.d(HomeActivity.TAG, TAG + ", MoviesPresenter: create!");
        mMoviesView.setPresenter(this);

        mCompositeSubscription = new CompositeSubscription();
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

        mCompositeSubscription.add(mIDuobanService.searchHotMoviesWithRxJava(movieStartIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HotMoviesInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "===> onCompleted 22: Thread.Id = " + Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "===> onError 22: Thread.Id = "
                                + Thread.currentThread().getId() + ", Error: " + e.getMessage());

                        processLoadMoreEmptyMovies();
                    }

                    @Override
                    public void onNext(HotMoviesInfo hotMoviesInfo) {
                        Log.d(TAG, "===> onNext 22: Thread.Id = " + Thread.currentThread().getId());
                        List<Movie> moreMoviesList = hotMoviesInfo.getMovies();
                        //debug
                        Log.e(HomeActivity.TAG, "===> hotMoviesInfo, size = " + moreMoviesList.size());

                        processLoadMoreMovies(moreMoviesList);
                    }
                }));
    }

    @Override
    public void cancelRetrofitRequest() {
        Log.d(TAG, TAG + "=> cancelRetrofitRequest() isCanceled = " + mMoviesRetrofitCallback.isCanceled());
        if(mMoviesRetrofitCallback != null && !mMoviesRetrofitCallback.isCanceled()) mMoviesRetrofitCallback.cancel();
    }

    @Override
    public void unSubscribe() {
        Log.d(TAG, TAG + "=> unSubscribe all subscribe");
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    private void loadMovies(boolean forceUpdate, final boolean showLoadingUI){
        if(forceUpdate) {
            mCompositeSubscription.add(mIDuobanService.searchHotMoviesWithRxJava(0)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(() -> {
                        if(showLoadingUI){
                            //MoviesFragment需要显示Loading 界面
                            mMoviesView.setRefreshedIndicator(true);
                        }
                    })
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<HotMoviesInfo>() {
                        @Override
                        public void onStart() {
                            Log.d(TAG, "===> onStart: Thread.Id = " + Thread.currentThread().getId());
                        }

                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "===> onCompleted 11: Thread.Id = " + Thread.currentThread().getId());
                            //获取数据成功，Loading UI消失
                            if(showLoadingUI) {
                                mMoviesView.setRefreshedIndicator(false);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "===> onError 11 : Thread.Id = "
                                    + Thread.currentThread().getId() + ", Error: " + e.getMessage());

                            //获取数据成功，Loading UI消失
                            if(showLoadingUI) {
                                mMoviesView.setRefreshedIndicator(false);
                            }
                            processEmptyMovies();
                        }

                        @Override
                        public void onNext(HotMoviesInfo hotMoviesInfo) {
                            Log.d(TAG, "===> onNext 11: Thread.Id = " + Thread.currentThread().getId());
                            List<Movie> moviesList = hotMoviesInfo.getMovies();

                            mMovieTotal = hotMoviesInfo.getTotal();

                            //debug
                            Log.e(HomeActivity.TAG, "===> hotMoviesInfo, size = " + moviesList.size()
                                    + " showLoadingUI: " + showLoadingUI + ", total = " + mMovieTotal);

                            processMovies(moviesList);
                        }
                    }));

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
