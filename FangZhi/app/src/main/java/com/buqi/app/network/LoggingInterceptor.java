package com.buqi.app.network;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.buqi.app.MyApplication;
import com.buqi.app.tools.log.KLog;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by smacr on 2016/11/23.
 */
public class LoggingInterceptor implements Interceptor {
    private boolean isShow;

    public LoggingInterceptor(boolean isShow) {
        this.isShow = isShow;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
            KLog.init(isShow);
            Request request = chain.request();
            KLog.e(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));

            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            KLog.e(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            KLog.json(content);
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) MyApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
