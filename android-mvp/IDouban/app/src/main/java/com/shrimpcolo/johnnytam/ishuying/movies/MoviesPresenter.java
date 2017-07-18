package com.shrimpcolo.johnnytam.ishuying.movies;

import android.support.annotation.NonNull;
import android.util.Log;

import com.shrimpcolo.johnnytam.ishuying.HomeActivity;
import com.shrimpcolo.johnnytam.ishuying.api.IDoubanService;
import com.shrimpcolo.johnnytam.ishuying.beans.HotMoviesInfo;
import com.shrimpcolo.johnnytam.ishuying.beans.Movie;

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

        Log.i(HomeActivity.TAG, TAG + ", MoviesPresenter: create!");
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

        Log.i(HomeActivity.TAG, "movieStartIndex: " + movieStartIndex + ", mMovieTotal: " + mMovieTotal);
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
                        Log.i(HomeActivity.TAG, "===> onCompleted 22: Thread.Id = " + Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(HomeActivity.TAG, "===> onError 22: Thread.Id = "
                                + Thread.currentThread().getId() + ", Error: " + e.getMessage());

                        processLoadMoreEmptyMovies();
                    }

                    @Override
                    public void onNext(HotMoviesInfo hotMoviesInfo) {
                        Log.i(HomeActivity.TAG, "===> onNext 22: Thread.Id = " + Thread.currentThread().getId());
                        List<Movie> moreMoviesList = hotMoviesInfo.getMovies();
                        //debug
                        Log.i(HomeActivity.TAG, "===> hotMoviesInfo, size = " + moreMoviesList.size());

                        processLoadMoreMovies(moreMoviesList);
                    }
                }));

    }

    @Override
    public void cancelRetrofitRequest() {
        Log.i(HomeActivity.TAG, TAG + "=> cancelRetrofitRequest() isCanceled = " + mMoviesRetrofitCallback.isCanceled());
        if(mMoviesRetrofitCallback != null && !mMoviesRetrofitCallback.isCanceled()) mMoviesRetrofitCallback.cancel();
    }

    @Override
    public void unSubscribe() {
        Log.i(HomeActivity.TAG, TAG + "=> unSubscribe all subscribe");
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    private void loadMovies(boolean forceUpdate, final boolean showLoadingUI){
//        if(showLoadingUI){
//            //MoviesFragment需要显示Loading 界面
//            mMoviesView.setRefreshedIndicator(true);
//        }

        if(forceUpdate) {
            mCompositeSubscription.add(mIDuobanService.searchHotMoviesWithRxJava(0)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(() -> {
                        Log.i(TAG, "doOnSubscribe");
                        if(showLoadingUI){
                            //MoviesFragment需要显示Loading 界面
                            mMoviesView.setRefreshedIndicator(true);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<HotMoviesInfo>() {

                        @Override
                        public void onStart() {
                            Log.i(TAG, "onStart-> mSubscription: " + mSubscription);
                        }

                        @Override
                        public void onCompleted() {
                            Log.i(HomeActivity.TAG, "===> onCompleted 11: Thread.Id = " + Thread.currentThread().getId());
                            //获取数据成功，Loading UI消失
                            if(showLoadingUI) {
                                mMoviesView.setRefreshedIndicator(false);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(HomeActivity.TAG, "===> onError 11 : Thread.Id = "
                                    + Thread.currentThread().getId() + ", Error: " + e.getMessage());

                            //获取数据成功，Loading UI消失
                            if(showLoadingUI) {
                                mMoviesView.setRefreshedIndicator(false);
                            }
                            processEmptyMovies();
                        }

                        @Override
                        public void onNext(HotMoviesInfo hotMoviesInfo) {
                            Log.i(HomeActivity.TAG, "===> onNext 11: Thread.Id = " + Thread.currentThread().getId());
                            List<Movie> moviesList = hotMoviesInfo.getMovies();

                            mMovieTotal = hotMoviesInfo.getTotal();

                            //debug
                            Log.i(HomeActivity.TAG, "===> hotMoviesInfo, size = " + moviesList.size()
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
