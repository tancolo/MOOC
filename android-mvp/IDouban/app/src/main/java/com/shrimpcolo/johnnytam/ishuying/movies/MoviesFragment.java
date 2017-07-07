package com.shrimpcolo.johnnytam.ishuying.movies;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.shrimpcolo.johnnytam.ishuying.HomeActivity;
import com.shrimpcolo.johnnytam.ishuying.R;
import com.shrimpcolo.johnnytam.ishuying.base.BaseFragment;
import com.shrimpcolo.johnnytam.ishuying.base.BaseRecycleViewAdapter;
import com.shrimpcolo.johnnytam.ishuying.base.BaseRecycleViewHolder;
import com.shrimpcolo.johnnytam.ishuying.beans.Movie;
import com.shrimpcolo.johnnytam.ishuying.moviedetail.MovieDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends BaseFragment<Movie> implements MoviesContract.View{
    private static final String TAG = MoviesFragment.class.getSimpleName();

    private MoviesContract.Presenter mPresenter;

    private View mNoMoviesView;

    private SwipeToLoadLayout mSwipeToLoadLayout;

    public MoviesFragment() {
        // Required empty public constructor
    }

    public static MoviesFragment newInstance(){
        Log.e(HomeActivity.TAG, "MoviesFragment newInstance!");
        return new MoviesFragment();
    }

    @Override
    protected void initVariables() {
        Log.e(HomeActivity.TAG,  TAG + " onCreate() -> initVariables");
        mAdapterData = new ArrayList<>();
    }

    @Override
    protected void initRecycleViewAdapter() {
        Log.e(HomeActivity.TAG,  TAG + " onCreate() -> initRecycleViewAdapter");
        //create movie adapter
        mAdapter = new BaseRecycleViewAdapter<>(new ArrayList<>(0),
                R.layout.recyclerview_movies_item,
                MovieViewHolder::new
        );
    }

    @Override
    protected void initRecycleView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(HomeActivity.TAG,  TAG + " onCreateView() -> initRecycleView");
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_movies, container, false);
        mSwipeToLoadLayout = (SwipeToLoadLayout) mView.findViewById(R.id.swipeToLoadLayout);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.swipe_target);
        mNoMoviesView = mView.findViewById(R.id.ll_no_movies);

        //set recycle view
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initSwipeRefreshLayout() {
        Log.e(HomeActivity.TAG,  TAG + " onCreateView() -> initSwipeRefreshLayout");
        mSwipeToLoadLayout.setOnRefreshListener(() -> {
            Log.e(HomeActivity.TAG, TAG + "=> onRefresh!");
            mPresenter.loadRefreshedMovies(true);
        });

        mSwipeToLoadLayout.setOnLoadMoreListener(() -> {
            Log.e(HomeActivity.TAG, TAG + "=> onLoadMore, item index is: " + mAdapter.getItemCount());
            mPresenter.loadMoreMovies(mAdapter.getItemCount());
        });
    }

    @Override
    protected void initEndlessScrollListener() {

    }

    @Override
    protected void startPresenter() {
        //Log.e(HomeActivity.TAG,  TAG + " onViewCreated(): Presenter!  "  + mPresenter);
        if(mPresenter != null) {
            mPresenter.start();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(mPresenter != null) {
            mPresenter.start();
        }

    }

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showRefreshedMovies(List<Movie> movies) {
        //If the refreshed data is a part of mAdapterMovieData, don't operate mAdapter
        if(mAdapterData.size() != 0 && movies.get(0).getId().equals(mAdapterData.get(0).getId())) {
            return;
        }

        mAdapterData.addAll(movies);
        mAdapter.replaceData(mAdapterData);

        mRecyclerView.setVisibility(View.VISIBLE);
        mNoMoviesView.setVisibility(View.GONE);

    }

    @Override
    public void showLoadedMoreMovies(List<Movie> movies) {
        mAdapterData.addAll(movies);
        mAdapter.replaceData(mAdapterData);

        mSwipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void showNoMovies() {
        mRecyclerView.setVisibility(View.GONE);
        mNoMoviesView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoLoadedMoreMovies() {
        Toast.makeText(getActivity().getApplicationContext(),
                getActivity().getString(R.string.content_no_loadmore), Toast.LENGTH_LONG).show();

        mSwipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void setRefreshedIndicator(boolean active) {
        if(getView() == null) return;

        Log.e(HomeActivity.TAG, TAG + "=> loading indicator: " + active);
        mSwipeToLoadLayout.post(() -> mSwipeToLoadLayout.setRefreshing(active));

    }

    static class MovieViewHolder extends BaseRecycleViewHolder<Movie> implements View.OnClickListener {

        ImageView mMovieImage;

        TextView mMovieTitle;

        RatingBar mMovieStars;

        TextView mMovieRatingAverage;


        public MovieViewHolder(View itemView) {
            super(itemView);

            mMovieImage = (ImageView) itemView.findViewById(R.id.movie_cover);
            mMovieTitle = (TextView) itemView.findViewById(R.id.movie_title);
            mMovieStars = (RatingBar) itemView.findViewById(R.id.rating_star);
            mMovieRatingAverage = (TextView) itemView.findViewById(R.id.movie_average);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.e(HomeActivity.TAG, "==> onClick....Item");

            if (itemContent == null || itemView == null) return;

            Context context = itemView.getContext();
            if (context == null) return;

            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("movie", itemContent);

            if (context instanceof Activity) {
                Activity activity = (Activity) context;

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, mMovieImage, "cover").toBundle();
                ActivityCompat.startActivity(activity, intent, bundle);
            }
        }

        @Override
        protected void onBindItem(Movie movie) {
            Context context = itemView.getContext();
            if (movie == null || context == null) return;

            Picasso.with(context)
                    .load(movie.getImages().getLarge())
                    .placeholder(context.getResources().getDrawable(R.mipmap.ic_ishuying))
                    .into(mMovieImage);

            mMovieTitle.setText(movie.getTitle());
            final double average = movie.getRating().getAverage();
            if (average == 0.0) {
                mMovieStars.setVisibility(View.GONE);
                mMovieRatingAverage.setText(context.getResources().getString(R.string.string_no_note));
            } else {
                mMovieStars.setVisibility(View.VISIBLE);
                mMovieRatingAverage.setText(String.valueOf(average));
                mMovieStars.setStepSize(0.5f);
                mMovieStars.setRating((float) (movie.getRating().getAverage() / 2));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapterData.clear();
        //mPresenter.cancelRetrofitRequest();
        mPresenter.unSubscribe();
        Log.e(HomeActivity.TAG, TAG + "=> onDestroy()!!!");
    }

}
