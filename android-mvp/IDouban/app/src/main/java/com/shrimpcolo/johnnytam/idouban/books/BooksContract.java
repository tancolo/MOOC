package com.shrimpcolo.johnnytam.idouban.books;

import com.shrimpcolo.johnnytam.idouban.BasePresenter;
import com.shrimpcolo.johnnytam.idouban.BaseView;
import com.shrimpcolo.johnnytam.idouban.beans.Book;

import java.util.List;

/**
 * Created by Johnny Tam on 2017/3/21.
 */

public interface BooksContract {

    interface View extends BaseView<Presenter> {
        void showBooks(List<Book> books);

        void showNoBooks();

        void setLoadingIndicator(boolean active);
    }

    interface Presenter extends BasePresenter {

        void loadBooks(boolean forceUpdate);

    }


}
