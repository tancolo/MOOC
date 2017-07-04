package com.shrimpcolo.johnnytam.idouban.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Johnny Tam on 2017/4/27.
 */

public class DoubanManager {
    private static IDoubanService sDoubanService;

    public synchronized static IDoubanService createDoubanService() {
        if (sDoubanService  == null) {
            Retrofit retrofit = createRetrofit();
            sDoubanService = retrofit.create(IDoubanService.class);
        }

        return sDoubanService;
    }

    private static Retrofit createRetrofit() {
        OkHttpClient httpClient;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();

        return new Retrofit.Builder()
                .baseUrl(IDoubanService.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                //.client(httpClient)
                .build();
    }
}
