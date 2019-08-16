package com.shrimpcolo.johnnytam.ishuying.api;

import com.shrimpcolo.johnnytam.ishuying.beans.BlogsInfo;
import com.shrimpcolo.johnnytam.ishuying.beans.BooksInfo;
import com.shrimpcolo.johnnytam.ishuying.beans.HotMoviesInfo;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Johnny Tam on 2017/4/27.
 */

public interface IDoubanService {
    String BASE_URL = "https://api.douban.com/v2/";

    @GET("movie/in_theaters?apikey=0df993c66c0c636e29ecbb5344252a4a")
    Call<HotMoviesInfo> searchHotMovies(@Query("start") int startIndex);

    @GET("movie/in_theaters?apikey=0df993c66c0c636e29ecbb5344252a4a")
    Observable<HotMoviesInfo> searchHotMoviesWithRxJava(@Query("start") int startIndex);

    @GET("book/search?apikey=0df993c66c0c636e29ecbb5344252a4a")
    Call<BooksInfo> searchBooks(@Query("q") String name, @Query("start") int index);

    @GET("book/search?apikey=0df993c66c0c636e29ecbb5344252a4a")
    Observable<BooksInfo> searchBooksWithRxJava(@Query("q") String name, @Query("start") int index);

    @GET
    Observable<BlogsInfo> getBlogWithRxJava(@Url String url);

}
