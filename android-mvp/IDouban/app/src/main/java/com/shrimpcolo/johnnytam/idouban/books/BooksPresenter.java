package com.shrimpcolo.johnnytam.idouban.books;

import android.support.annotation.NonNull;
import android.util.Log;

import com.shrimpcolo.johnnytam.idouban.HomeActivity;
import com.shrimpcolo.johnnytam.idouban.api.IDoubanService;
import com.shrimpcolo.johnnytam.idouban.beans.Book;
import com.shrimpcolo.johnnytam.idouban.beans.BooksInfo;

import java.util.List;

import retrofit2.Call;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Johnny Tam on 2017/3/21.
 */

public class BooksPresenter implements BooksContract.Presenter{

    private static final String TAG = BooksPresenter.class.getSimpleName();

    private BooksContract.View mBookView;

    private final IDoubanService mIDuobanService;

    private boolean mFirstLoad = true;

    private Call<BooksInfo> mBooksRetrofitCallback;

    private CompositeSubscription mCompositeSubscription;

    public BooksPresenter(@NonNull IDoubanService booksService, @NonNull BooksContract.View bookFragment) {
        mIDuobanService = booksService;
        mBookView = bookFragment;

        mBookView.setPresenter(this);

        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void loadRefreshedBooks(boolean forceUpdate) {
        loadBooks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void loadMoreBooks(int start) {

        mCompositeSubscription.add(mIDuobanService.searchBooksWithRxJava("黑客与画家", start)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BooksInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.d(HomeActivity.TAG, "===> Load More Book: onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(HomeActivity.TAG, "===> onError: Thread.Id = "
                                + Thread.currentThread().getId() + ", Error: " + e.getMessage());

                        processLoadMoreEmptyBooks();
                    }

                    @Override
                    public void onNext(BooksInfo booksInfo) {
                        List<Book> loadMoreList = booksInfo.getBooks();
                        //debug
                        Log.d(HomeActivity.TAG, "===> Load More Book: onNext, size = " + loadMoreList.size());

                        processLoadMoreBooks(loadMoreList);
                    }
                }));

    }

    @Override
    public void cancelRetrofitRequest() {
        Log.e(HomeActivity.TAG, TAG + "=> cancelRetrofitRequest() isCanceled = "
                + mBooksRetrofitCallback.isCanceled());

        if(!mBooksRetrofitCallback.isCanceled()) mBooksRetrofitCallback.cancel();
    }

    @Override
    public void unSubscribe() {
        Log.d(HomeActivity.TAG, TAG + "=> unSubscribe all subscribe");
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void start() {
        loadRefreshedBooks(false);
    }

    private void loadBooks(boolean forceUpdate, final boolean showLoadingUI) {
        if(showLoadingUI){
            //BooksFragment需要显示Loading 指示器
            mBookView.setRefreshedIndicator(true);
        }

        if(forceUpdate) {
            mCompositeSubscription.add(mIDuobanService.searchBooksWithRxJava("黑客与画家", 0)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BooksInfo>() {
                        @Override
                        public void onCompleted() {
                            Log.d(HomeActivity.TAG, "===> Search Book: onNext " + ", showLoadingUI: " + showLoadingUI);
                            //获取数据成功，Loading UI消失
                            if(showLoadingUI) {
                                mBookView.setRefreshedIndicator(false);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(HomeActivity.TAG, "===> search Book: onError: Thread.Id = "
                                    + Thread.currentThread().getId() + ", Error: " + e.getMessage());

                            //获取数据成功，Loading UI消失
                            if(showLoadingUI) {
                                mBookView.setRefreshedIndicator(false);
                            }
                            processEmptyBooks();
                        }

                        @Override
                        public void onNext(BooksInfo booksInfo) {
                            List<Book> booksList = booksInfo.getBooks();
                            //debug
                            Log.d(HomeActivity.TAG, "===> Search Book: onNext, size = " + booksList.size());

                            processBooks(booksList);
                        }
                    }));
        }
    }

    private void processBooks(List<Book> books) {
        if (books.isEmpty()) processEmptyBooks();// Show a message indicating there are no movies for users
        else mBookView.showRefreshedBooks(books); // Show the list of books
    }

    private void processEmptyBooks() {
        //BooksFragment需要给出提示
        mBookView.showNoBooks();
    }

    private void processLoadMoreBooks(List<Book> books){

        if (books.isEmpty()) processLoadMoreEmptyBooks();
        else mBookView.showLoadedMoreBooks(books);
    }

    private void processLoadMoreEmptyBooks() {
        Log.e(TAG, "LoadMore Empty books ");
        mBookView.showNoLoadedMoreBooks();
    }

}
