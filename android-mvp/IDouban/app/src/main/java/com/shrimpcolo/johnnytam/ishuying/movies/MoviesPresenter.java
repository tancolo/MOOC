package com.shrimpcolo.johnnytam.ishuying.movies;

import android.util.Log;

import com.shrimpcolo.johnnytam.ishuying.HomeActivity;
import com.shrimpcolo.johnnytam.ishuying.api.IDoubanService;
import com.shrimpcolo.johnnytam.ishuying.beans.HotMoviesInfo;
import com.shrimpcolo.johnnytam.ishuying.beans.Movie;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;

import static com.google.common.base.Preconditions.checkNotNull;

public class MoviesPresenter implements MoviesContract.Presenter {

    private final static String TAG = MoviesPresenter.class.getSimpleName();

    private final MoviesContract.View mMoviesView;

    private final IDoubanService mIDuobanService;

    private boolean mFirstLoad = true;

    private int mMovieTotal;

    private Call<HotMoviesInfo> mMoviesRetrofitCallback;

    private Disposable mDisposable;

    private CompositeDisposable mCompositeDisposable;

    public MoviesPresenter(@NonNull IDoubanService moviesService, @NonNull MoviesContract.View moviesView) {
        mIDuobanService = checkNotNull(moviesService, "IDoubanServie cannot be null!");
        mMoviesView = checkNotNull(moviesView, "moviesView cannot be null!");

        Log.i(HomeActivity.TAG, TAG + ", MoviesPresenter: create!");
        mMoviesView.setPresenter(this);

        mCompositeDisposable = new CompositeDisposable();
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

        mIDuobanService.searchHotMoviesWithRxJava(movieStartIndex)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotMoviesInfo>() {

                    @Override
                    public void onError(Throwable e) {
                        Log.i(HomeActivity.TAG, "===> onError 22: Thread.Id = "
                                + Thread.currentThread().getId() + ", Error: " + e.getMessage());

                        processLoadMoreEmptyMovies();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(HomeActivity.TAG, "===> onCompleted 22: Thread.Id = " + Thread.currentThread().getId());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mCompositeDisposable.add(disposable);
                    }

                    @Override
                    public void onNext(HotMoviesInfo hotMoviesInfo) {
                        Log.i(HomeActivity.TAG, "===> onNext 22: Thread.Id = " + Thread.currentThread().getId());
                        List<Movie> moreMoviesList = hotMoviesInfo.getMovies();
                        //debug
                        Log.i(HomeActivity.TAG, "===> hotMoviesInfo, size = " + moreMoviesList.size());

                        processLoadMoreMovies(moreMoviesList);
                    }
                });

    }

    @Override
    public void cancelRetrofitRequest() {
        Log.i(HomeActivity.TAG, TAG + "=> cancelRetrofitRequest() isCanceled = " + mMoviesRetrofitCallback.isCanceled());
        if(mMoviesRetrofitCallback != null && !mMoviesRetrofitCallback.isCanceled()) mMoviesRetrofitCallback.cancel();
    }

    @Override
    public void unSubscribe() {
        Log.i(HomeActivity.TAG, TAG + "=> unSubscribe all subscribe");
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    private void loadMovies(boolean forceUpdate, final boolean showLoadingUI){
//        if(showLoadingUI){
//            //MoviesFragment需要显示Loading 界面
//            mMoviesView.setRefreshedIndicator(true);
//        }

        if(forceUpdate) {
            mIDuobanService.searchHotMoviesWithRxJava(0)
                    .doOnSubscribe((disposable) -> {
                        Log.i(TAG, "doOnSubscribe");
                        if(showLoadingUI){
                            //MoviesFragment需要显示Loading 界面
                            mMoviesView.setRefreshedIndicator(true);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<HotMoviesInfo>() {

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
                        public void onComplete() {
                            Log.i(HomeActivity.TAG, "===> onCompleted 11: Thread.Id = " + Thread.currentThread().getId());
                            //获取数据成功，Loading UI消失
                            if(showLoadingUI) {
                                mMoviesView.setRefreshedIndicator(false);
                            }
                        }

                        @Override
                        public void onSubscribe(@NonNull Disposable disposable) {
                            mCompositeDisposable.add(disposable);
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
