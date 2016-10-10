package com.fangzhi.app;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.litepal.LitePalApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by zhangyu on 2016/5/9.
 */
public class MyApplication extends LitePalApplication {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        AutoLayoutConifg.getInstance().useDeviceSize();
        Fresco.initialize(this);
        //配置
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG",true))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
        mContext =  getApplicationContext();

    }
    public static Context getContext(){
        return mContext;
    }
}
