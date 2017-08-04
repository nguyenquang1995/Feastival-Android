package com.framgia.feastival.data.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tmd on 13/05/2017.
 */
public class ServiceGenerator {
    public static final String BASE_URL = "http://feastival-react.herokuapp.com/api/";
    private static Retrofit sRetrofit = null;
    private static Retrofit.Builder sRetrofitBuilder = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create());
    private static HttpLoggingInterceptor sHttpLoggingInterceptor = new HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder sOkHttpClientBuilder = new OkHttpClient.Builder()
        .addInterceptor(sHttpLoggingInterceptor);
    private static OkHttpClient sOkHttpClient = sOkHttpClientBuilder.build();

    public static <T> T createService(Class<T> serviceClass) {
        if (sRetrofit == null) {
            sRetrofit = sRetrofitBuilder.client(sOkHttpClient).build();
        }
        return sRetrofit.create(serviceClass);
    }
}
