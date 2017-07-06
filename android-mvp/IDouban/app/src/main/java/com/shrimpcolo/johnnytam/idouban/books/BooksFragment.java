package com.shrimpcolo.johnnytam.idouban.books;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shrimpcolo.johnnytam.idouban.HomeActivity;
import com.shrimpcolo.johnnytam.idouban.R;
import com.shrimpcolo.johnnytam.idouban.base.BaseFragment;
import com.shrimpcolo.johnnytam.idouban.base.BaseRecycleViewAdapter;
import com.shrimpcolo.johnnytam.idouban.base.BaseRecycleViewHolder;
import com.shrimpcolo.johnnytam.idouban.beans.Book;
import com.shrimpcolo.johnnytam.idouban.bookdetail.BookDetailActivity;
import com.shrimpcolo.johnnytam.idouban.utils.ConstContent;
import com.shrimpcolo.johnnytam.idouban.ui.EndlessRecyclerViewScrollListener;
import com.shrimpcolo.johnnytam.idouban.ui.ScrollChildSwipeRefreshLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends BaseFragment<Book> implements BooksContract.View {
    private static final String TAG = BooksFragment.class.getSimpleName();

    private BooksContract.Presenter mPresenter;

    private View mNoBooksView;

    public BooksFragment() {
        // Required empty public constructor
    }

    public static BooksFragment newInstance() {
        return new BooksFragment();
    }

    @Override
    protected void initVariables() {
        Log.e(HomeActivity.TAG,  TAG + " onCreate() -> initVariables");
        mAdapterData = new ArrayList<>();
    }

    @Override
    protected void initRecycleViewAdapter() {
        mAdapter = new BaseRecycleViewAdapter<>(new ArrayList<>(0),
                R.layout.recyclerview_book_item,
                BookViewHolder::new);
    }

    @Override
    protected void initRecycleView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView  = inflater.inflate(R.layout.fragment_books, container, false);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_books);
        mNoBooksView = mView.findViewById(R.id.ll_no_books);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initSwipeRefreshLayout() {
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) mView.findViewById(R.id.book_refresh_layout);

        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(mRecyclerView);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            Log.e(HomeActivity.TAG, "\n\n onRefresh loadRefreshedBooks...");
            mPresenter.loadRefreshedBooks(true);
        });
    }

    @Override
    protected void initEndlessScrollListener() {
        EndlessRecyclerViewScrollListener endlessScrollListener =
                new EndlessRecyclerViewScrollListener(mLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.e(TAG, "page: " + page + ", totalItemsCount: " + totalItemsCount);
                mPresenter.loadMoreBooks(totalItemsCount);
            }
        };

        mRecyclerView.addOnScrollListener(endlessScrollListener);
    }

    @Override
    protected void startPresenter() {
        if(mPresenter != null){
            mPresenter.start();
        }
    }

    @Override
    public void showRefreshedBooks(List<Book> books) {

        //If the refreshed data is a part of mAdapterBooksData, don't operate mBookAdapter
        if(mAdapterData.size() != 0
                && books.get(0).getId().equals(mAdapterData.get(0).getId())) {
            return;
        }

        mAdapterData.addAll(books);
        mAdapter.replaceData(mAdapterData);

        mRecyclerView.setVisibility(View.VISIBLE);
        mNoBooksView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadedMoreBooks(List<Book> books) {
        mAdapterData.addAll(books);
        mAdapter.replaceData(mAdapterData);
    }

    @Override
    public void showNoBooks() {
        mRecyclerView.setVisibility(View.GONE);
        mNoBooksView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoLoadedMoreBooks() {
        //Log.e(TAG, "===> LoadMore Empty books thread id: " + Thread.currentThread().getId());
        Toast.makeText(getActivity().getApplicationContext(),
                getActivity().getString(R.string.content_no_loadmore), Toast.LENGTH_LONG).show();
    }

    @Override
    public void setRefreshedIndicator(boolean active) {
        if(getView() == null) return;

        Log.e(HomeActivity.TAG, TAG + "=> loading indicator: " + active);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)getView().findViewById(R.id.book_refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        swipeRefreshLayout.post(() -> {
            Log.e(HomeActivity.TAG, "swipeRefreshLayout run() active: " + active);
            swipeRefreshLayout.setRefreshing(active);
        });
    }

    @Override
    public void setPresenter(BooksContract.Presenter presenter) {
        mPresenter = presenter;
    }

    static class BookViewHolder extends BaseRecycleViewHolder<Book> implements View.OnClickListener {

        CardView cardView;
        ImageView bookImage;
        TextView bookTitle;
        TextView bookAuthor;
        TextView bookSubTitle;
        TextView bookPubDate;
        TextView bookPages;
        TextView bookPrice;

        public BookViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            bookImage = (ImageView) itemView.findViewById(R.id.book_cover);
            bookTitle = (TextView) itemView.findViewById(R.id.txt_title);
            bookAuthor = (TextView) itemView.findViewById(R.id.txt_author);
            bookSubTitle = (TextView) itemView.findViewById(R.id.txt_subTitle);
            bookPubDate = (TextView) itemView.findViewById(R.id.txt_pubDate);
            bookPrice = (TextView) itemView.findViewById(R.id.txt_prices);
            bookPages = (TextView) itemView.findViewById(R.id.txt_pages);

            itemView.setOnClickListener(this);
        }

        @Override
        protected void onBindItem(Book book) {
            Context context = itemView.getContext();
            if (book == null || context == null) return;

            //get the prefix string
            String prefixSubTitle = context.getString(R.string.prefix_subtitle);
            String prefixAuthor = context.getString(R.string.prefix_author);
            String prefixPubDate = context.getString(R.string.prefix_pubdata);
            String prefixPages = context.getString(R.string.prefix_pages);
            String prefixPrice = context.getString(R.string.prefix_price);

            bookTitle.setText(book.getTitle());
            bookAuthor.setText(String.format(prefixAuthor, book.getAuthor()));
            bookSubTitle.setText(String.format(prefixSubTitle, book.getSubtitle()));
            bookPubDate.setText(String.format(prefixPubDate, book.getPubdate()));
            bookPages.setText(String.format(prefixPages, book.getPages()));
            bookPrice.setText(String.format(prefixPrice, book.getPrice()));

            Picasso.with(context)
                    .load(book.getImages().getLarge())
                    .placeholder(context.getResources().getDrawable(R.mipmap.ic_launcher))
                    .into(bookImage);
        }

        @Override
        public void onClick(View v) {
            Log.e(HomeActivity.TAG, "==>Book onClick....Item");

            if (itemContent == null) return;
            if (itemView == null) return;

            Context context = itemView.getContext();
            if (context == null) return;

            Intent intent = new Intent(context, BookDetailActivity.class);
            intent.putExtra(ConstContent.INTENT_EXTRA_BOOK, itemContent);

            if (context instanceof Activity) {
                Activity activity = (Activity) context;

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, bookImage, "cover").toBundle();
                ActivityCompat.startActivity(activity, intent, bundle);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mAdapterData.clear();
        mPresenter.unSubscribe();
        Log.e(HomeActivity.TAG, TAG + "=> onDestroy()!!!");
    }
}
