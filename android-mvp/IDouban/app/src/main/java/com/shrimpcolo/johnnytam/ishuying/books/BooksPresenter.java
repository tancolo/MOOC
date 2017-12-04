package com.shrimpcolo.johnnytam.ishuying.books;

import android.util.Log;

import com.shrimpcolo.johnnytam.ishuying.HomeActivity;
import com.shrimpcolo.johnnytam.ishuying.api.IDoubanService;
import com.shrimpcolo.johnnytam.ishuying.beans.Book;
import com.shrimpcolo.johnnytam.ishuying.beans.BooksInfo;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;


/**
 * Created by Johnny Tam on 2017/3/21.
 */

public class BooksPresenter implements BooksContract.Presenter {

    private static final String TAG = BooksPresenter.class.getSimpleName();

    private BooksContract.View mBookView;

    private final IDoubanService mIDuobanService;

    private boolean mFirstLoad = true;

    private Call<BooksInfo> mBooksRetrofitCallback;

    private CompositeDisposable compositeDisposable;

    public BooksPresenter(@NonNull IDoubanService booksService, @NonNull BooksContract.View bookFragment) {
        mIDuobanService = booksService;
        mBookView = bookFragment;

        mBookView.setPresenter(this);

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadRefreshedBooks(boolean forceUpdate) {
        loadBooks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void loadMoreBooks(int start) {

        mIDuobanService.searchBooksWithRxJava("黑客与画家", start)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BooksInfo>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        compositeDisposable.add(disposable);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "===> onError: Thread.Id = "
                                + Thread.currentThread().getId() + ", Error: " + e.getMessage());

                        processLoadMoreEmptyBooks();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "===> Load More Book: onCompleted");
                    }

                    @Override
                    public void onNext(BooksInfo booksInfo) {
                        List<Book> loadMoreList = booksInfo.getBooks();
                        //debug
                        Log.i(TAG, "===> Load More Book: onNext, size = " + loadMoreList.size());

                        processLoadMoreBooks(loadMoreList);
                    }
                });
    }

    @Override
    public void cancelRetrofitRequest() {
        Log.i(HomeActivity.TAG, TAG + "=> cancelRetrofitRequest() isCanceled = "
                + mBooksRetrofitCallback.isCanceled());

        if (!mBooksRetrofitCallback.isCanceled()) mBooksRetrofitCallback.cancel();
    }

    @Override
    public void unSubscribe() {
        Log.i(HomeActivity.TAG, TAG + "=> unSubscribe all subscribe");
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void start() {
        loadRefreshedBooks(false);
    }

    private void loadBooks(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            //BooksFragment需要显示Loading 指示器
            mBookView.setRefreshedIndicator(true);
        }

        if (forceUpdate) {
            mIDuobanService.searchBooksWithRxJava("黑客与画家", 0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BooksInfo>() {

                        @Override
                        public void onSubscribe(@NonNull Disposable disposable) {
                            compositeDisposable.add(disposable);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "===> search Book: onError: Thread.Id = "
                                    + Thread.currentThread().getId() + ", Error: " + e.getMessage());

                            //获取数据成功，Loading UI消失
                            if (showLoadingUI) {
                                mBookView.setRefreshedIndicator(false);
                            }
                            processEmptyBooks();
                        }

                        @Override
                        public void onComplete() {
                            Log.i(HomeActivity.TAG, "===> Search Book: onNext " + ", showLoadingUI: " + showLoadingUI);
                            //获取数据成功，Loading UI消失
                            if (showLoadingUI) {
                                mBookView.setRefreshedIndicator(false);
                            }
                        }

                        @Override
                        public void onNext(BooksInfo booksInfo) {
                            List<Book> booksList = booksInfo.getBooks();
                            //debug
                            Log.i(HomeActivity.TAG, "===> Search Book: onNext, size = " + booksList.size());

                            processBooks(booksList);
                        }
                    });
        }
    }

    private void processBooks(List<Book> books) {
        if (books.isEmpty())
            processEmptyBooks();// Show a message indicating there are no movies for users
        else mBookView.showRefreshedBooks(books); // Show the list of books
    }

    private void processEmptyBooks() {
        //BooksFragment需要给出提示
        mBookView.showNoBooks();
    }

    private void processLoadMoreBooks(List<Book> books) {

        if (books.isEmpty()) processLoadMoreEmptyBooks();
        else mBookView.showLoadedMoreBooks(books);
    }

    private void processLoadMoreEmptyBooks() {
        Log.i(TAG, "LoadMore Empty books ");
        mBookView.showNoLoadedMoreBooks();
    }

}
