package com.shrimpcolo.johnnytam.idouban.books;

import android.support.annotation.NonNull;
import android.util.Log;

import com.shrimpcolo.johnnytam.idouban.HomeActivity;
import com.shrimpcolo.johnnytam.idouban.api.IDoubanService;
import com.shrimpcolo.johnnytam.idouban.beans.Book;
import com.shrimpcolo.johnnytam.idouban.beans.BooksInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Johnny Tam on 2017/3/21.
 */

public class BooksPresenter implements BooksContract.Presenter{

    private static final String TAG = BooksPresenter.class.getSimpleName();

    private BooksContract.View mBookView;

    private final IDoubanService mIDuobanService;

    private boolean mFirstLoad = true;

    private Call<BooksInfo> mBooksRetrofitCallback;

    public BooksPresenter(@NonNull IDoubanService booksService, @NonNull BooksContract.View bookFragment) {
        mIDuobanService = booksService;
        mBookView = bookFragment;

        mBookView.setPresenter(this);
    }

    @Override
    public void loadRefreshedBooks(boolean forceUpdate) {
        //loadBooks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void loadMoreBooks(int start) {

        mBooksRetrofitCallback = mIDuobanService.searchBooks("黑客与画家", start);
        mBooksRetrofitCallback.enqueue(new Callback<BooksInfo>() {
            @Override
            public void onResponse(Call<BooksInfo> call, Response<BooksInfo> response) {

                List<Book> loadMoreList = response.body().getBooks();
                //debug
                Log.e(HomeActivity.TAG, "===> Load More Book: Response, size = " + loadMoreList.size());

                processLoadMoreBooks(response.body().getBooks());
            }

            @Override
            public void onFailure(Call<BooksInfo> call, Throwable t) {
                Log.d(HomeActivity.TAG, "===> onFailure: Thread.Id = "
                        + Thread.currentThread().getId() + ", Error: " + t.getMessage());

                processLoadMoreEmptyBooks();
            }
        });

    }

    @Override
    public void cancelRetrofitRequest() {
        Log.e(HomeActivity.TAG, TAG + "=> cancelRetrofitRequest() isCanceled = "
                + mBooksRetrofitCallback.isCanceled());

        if(!mBooksRetrofitCallback.isCanceled()) mBooksRetrofitCallback.cancel();
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
            mBooksRetrofitCallback = mIDuobanService.searchBooks("黑客与画家", 0);
            mBooksRetrofitCallback.enqueue(new Callback<BooksInfo>() {
                @Override
                public void onResponse(Call<BooksInfo> call, Response<BooksInfo> response) {

                    List<Book> booksList = response.body().getBooks();
                    //debug
                    Log.e(HomeActivity.TAG, "===> Search Book: Response, size = " + booksList.size()
                            + " showLoadingUI: " + showLoadingUI);

                    //获取数据成功，Loading UI消失
                    if(showLoadingUI) {
                        mBookView.setRefreshedIndicator(false);
                    }

                    processBooks(response.body().getBooks());
                }

                @Override
                public void onFailure(Call<BooksInfo> call, Throwable t) {
                    Log.d(HomeActivity.TAG, "===> onFailure: Thread.Id = "
                            + Thread.currentThread().getId() + ", Error: " + t.getMessage());

                    //获取数据成功，Loading UI消失
                    if(showLoadingUI) {
                        mBookView.setRefreshedIndicator(false);
                    }
                    processEmptyBooks();
                }
            });
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
