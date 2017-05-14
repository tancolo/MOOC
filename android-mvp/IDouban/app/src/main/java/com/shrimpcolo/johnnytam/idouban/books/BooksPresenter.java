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

    public BooksPresenter(@NonNull IDoubanService booksService, @NonNull BooksContract.View bookFragment) {
        mIDuobanService = booksService;
        mBookView = bookFragment;

        mBookView.setPresenter(this);
    }

    @Override
    public void loadBooks(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadBooks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void start() {
        loadBooks(false);
    }

    private void loadBooks(boolean forceUpdate, final boolean showLoadingUI){
        if(showLoadingUI){
            //BooksFragment需要显示Loading 界面
            mBookView.setLoadingIndicator(true);
        }

        if(forceUpdate){
            mIDuobanService.searchBooks("黑客与画家").enqueue(new Callback<BooksInfo>() {
                @Override
                public void onResponse(Call<BooksInfo> call, Response<BooksInfo> response) {

                    List<Book> booksList = response.body().getBooks();
                    //debug
                    Log.e(HomeActivity.TAG, "===> Search Book: Response, size = " + booksList.size()
                            + " showLoadingUI: " + showLoadingUI);

                    //获取数据成功，Loading UI消失
                    if(showLoadingUI) {
                        mBookView.setLoadingIndicator(false);
                    }

                    processBooks(response.body().getBooks());
                }

                @Override
                public void onFailure(Call<BooksInfo> call, Throwable t) {
                    Log.d(HomeActivity.TAG, "===> onFailure: Thread.Id = "
                            + Thread.currentThread().getId() + ", Error: " + t.getMessage());

                    //获取数据成功，Loading UI消失
                    if(showLoadingUI) {
                        mBookView.setLoadingIndicator(false);
                    }
                    processEmptyTasks();
                }
            });
        }
    }

    private void processBooks(List<Book> books) {
        if (books.isEmpty()) {
            // Show a message indicating there are no movies for users
            processEmptyTasks();
        } else {
            // Show the list of books
            mBookView.showBooks(books);
        }
    }

    private void processEmptyTasks() {
        //BooksFragment需要给出提示
        mBookView.showNoBooks();
    }

}
