package com.shrimpcolo.johnnytam.idouban.movies;

import android.support.annotation.NonNull;
import android.util.Log;

import com.shrimpcolo.johnnytam.idouban.HomeActivity;
import com.shrimpcolo.johnnytam.idouban.api.DoubanManager;
import com.shrimpcolo.johnnytam.idouban.api.IDoubanService;
import com.shrimpcolo.johnnytam.idouban.beans.HotMoviesInfo;
import com.shrimpcolo.johnnytam.idouban.beans.Movie;

import java.util.List;

import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

public class MoviesPresenter implements MoviesContract.Presenter {

    private final static String TAG = MoviesPresenter.class.getSimpleName();

    private final MoviesContract.View mMoviesView;

    private final IDoubanService mIDuobanService;

    private boolean mFirstLoad = true;

    private int mMovieTotal;

    private Call<HotMoviesInfo> mMoviesRetrofitCallback;

    private Subscription mSubscription;

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

//        mMoviesRetrofitCallback = mIDuobanService.searchHotMovies(movieStartIndex);
//        mMoviesRetrofitCallback.enqueue(new Callback<HotMoviesInfo>() {
//            @Override
//            public void onResponse(Call<HotMoviesInfo> call, Response<HotMoviesInfo> response) {
//                Log.d(HomeActivity.TAG, "===> onResponse: Thread.Id = " + Thread.currentThread().getId());
//                List<Movie> moreMoviesList = response.body().getMovies();
//                //debug
//                Log.e(HomeActivity.TAG, "===> Response, size = " + moreMoviesList.size());
//
//                processLoadMoreMovies(moreMoviesList);
//            }
//
//            @Override
//            public void onFailure(Call<HotMoviesInfo> call, Throwable t) {
//                Log.d(HomeActivity.TAG, "===> onFailure: Thread.Id = "
//                        + Thread.currentThread().getId() + ", Error: " + t.getMessage());
//
//                processLoadMoreEmptyMovies();
//            }
//        });

        Observable<HotMoviesInfo> observable = DoubanManager.createDoubanService().searchHotMoviesWithRxJava(movieStartIndex);

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HotMoviesInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.d(HomeActivity.TAG, "===> onCompleted 22: Thread.Id = " + Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(HomeActivity.TAG, "===> onError 22: Thread.Id = "
                                + Thread.currentThread().getId() + ", Error: " + e.getMessage());

                        processLoadMoreEmptyMovies();
                    }

                    @Override
                    public void onNext(HotMoviesInfo hotMoviesInfo) {
                        Log.d(HomeActivity.TAG, "===> onNext 22: Thread.Id = " + Thread.currentThread().getId());
                        List<Movie> moreMoviesList = hotMoviesInfo.getMovies();
                        //debug
                        Log.e(HomeActivity.TAG, "===> hotMoviesInfo, size = " + moreMoviesList.size());

                        processLoadMoreMovies(moreMoviesList);
                    }
                });

    }

    @Override
    public void cancelRetrofitRequest() {
        Log.d(HomeActivity.TAG, TAG + "=> cancelRetrofitRequest() isCanceled = " + mMoviesRetrofitCallback.isCanceled());
        if(mMoviesRetrofitCallback != null && !mMoviesRetrofitCallback.isCanceled()) mMoviesRetrofitCallback.cancel();
    }

    @Override
    public void unSubscribe() {
        Log.d(HomeActivity.TAG, TAG + "=> unSubscribe() isUnSubscribed = " + mSubscription.isUnsubscribed());
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    private void loadMovies(boolean forceUpdate, final boolean showLoadingUI){
        if(showLoadingUI){
            //MoviesFragment需要显示Loading 界面
            mMoviesView.setRefreshedIndicator(true);
        }

        if(forceUpdate) {
//            mMoviesRetrofitCallback = mIDuobanService.searchHotMovies(0);
//            mMoviesRetrofitCallback.enqueue(new Callback<HotMoviesInfo>() {
//                @Override
//                public void onResponse(Call<HotMoviesInfo> call, Response<HotMoviesInfo> response) {
//                    Log.d(HomeActivity.TAG, "===> onResponse: Thread.Id = " + Thread.currentThread().getId());
//                    List<Movie> moviesList = response.body().getMovies();
//
//                    mMovieTotal = response.body().getTotal();
//                    //debug
//                    Log.e(HomeActivity.TAG, "===> Response, size = " + moviesList.size()
//                            + " showLoadingUI: " + showLoadingUI + ", total = " + mMovieTotal);
//
//                    //获取数据成功，Loading UI消失
//                    if(showLoadingUI) {
//                        mMoviesView.setRefreshedIndicator(false);
//                    }
//
//                    processMovies(moviesList);
//                }
//
//                @Override
//                public void onFailure(Call<HotMoviesInfo> call, Throwable t) {
//                    Log.d(HomeActivity.TAG, "===> onFailure: Thread.Id = "
//                            + Thread.currentThread().getId() + ", Error: " + t.getMessage());
//
//                    //获取数据成功，Loading UI消失
//                    if(showLoadingUI) {
//                        mMoviesView.setRefreshedIndicator(false);
//                    }
//                    processEmptyMovies();
//                }
//            });

            Observable<HotMoviesInfo> observable = mIDuobanService.searchHotMoviesWithRxJava(0);
            Log.e(TAG, "observable: " + observable);

            mSubscription = observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<HotMoviesInfo>() {

                        @Override
                        public void onStart() {
                            Log.e(TAG, "onStart-> mSubscription: " + mSubscription);
                        }

                        @Override
                        public void onCompleted() {
                            Log.d(HomeActivity.TAG, "===> onCompleted 11: Thread.Id = " + Thread.currentThread().getId());
                            //获取数据成功，Loading UI消失
                            if(showLoadingUI) {
                                mMoviesView.setRefreshedIndicator(false);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(HomeActivity.TAG, "===> onError 11 : Thread.Id = "
                                    + Thread.currentThread().getId() + ", Error: " + e.getMessage());

                            //获取数据成功，Loading UI消失
                            if(showLoadingUI) {
                                mMoviesView.setRefreshedIndicator(false);
                            }
                            processEmptyMovies();
                        }

                        @Override
                        public void onNext(HotMoviesInfo hotMoviesInfo) {
                            Log.d(HomeActivity.TAG, "===> onNext 11: Thread.Id = " + Thread.currentThread().getId());
                            List<Movie> moviesList = hotMoviesInfo.getMovies();

                            mMovieTotal = hotMoviesInfo.getTotal();

                            //debug
                            Log.e(HomeActivity.TAG, "===> hotMoviesInfo, size = " + moviesList.size()
                                    + " showLoadingUI: " + showLoadingUI + ", total = " + mMovieTotal);

                            processMovies(moviesList);
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
