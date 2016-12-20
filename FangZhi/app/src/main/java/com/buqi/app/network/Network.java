package com.buqi.app.network;

import com.zhy.http.okhttp.OkHttpUtils;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhangyu on 2016/6/15.
 */
 public class Network {
    private static ApiService apiService;

    private static final Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static final CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    public static ApiService getApiService() {
        if (apiService == null) {
            apiService = getRetrofit().create(ApiService.class);
        }
        return apiService;
    }


    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .callFactory(OkHttpUtils.getInstance().getOkHttpClient())
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }
}
