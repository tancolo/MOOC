package com.shrimpcolo.johnnytam.idouban.api;

import com.shrimpcolo.johnnytam.idouban.beans.BooksInfo;
import com.shrimpcolo.johnnytam.idouban.beans.HotMoviesInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Johnny Tam on 2017/4/27.
 */

public interface IDoubanService {
    String BASE_URL = "https://api.douban.com/v2/";

    @GET("movie/in_theaters")
    Call<HotMoviesInfo> searchHotMovies(@Query("start") int startIndex);

    @GET("movie/in_theaters")
    Observable<HotMoviesInfo> searchHotMoviesWithRxJava(@Query("start") int startIndex);

    @GET("book/search")
    Call<BooksInfo> searchBooks(@Query("q") String name, @Query("start") int index);
}
