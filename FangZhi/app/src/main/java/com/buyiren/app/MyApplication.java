package com.buyiren.app;

import android.app.ActivityManager;
import android.content.Context;

import com.buyiren.app.network.LoggingInterceptor;
import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zhy.http.okhttp.OkHttpUtils;

import org.litepal.LitePalApplication;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import okhttp3.OkHttpClient;

/**
 * Created by zhangyu on 2016/5/9.
 */
public class MyApplication extends LitePalApplication {
    private static final String MAIN_PROCESS = "com.buyiren.app";
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        String processName = getProcessName(this, android.os.Process.myPid());
        if (processName != null) {
            if (MAIN_PROCESS.equals(processName)) {
                //请求缓存
                //   File cacheFile = new File(getCacheDir(), "Test");
                //   Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);
                AutoLayoutConifg.getInstance().useDeviceSize();
                //配置
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new LoggingInterceptor(true))
//                        .cache(cache)
//                        .addInterceptor(new CacheInterceptor())
//                        .addNetworkInterceptor(new CacheInterceptor())
                        .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                        .readTimeout(10000L, TimeUnit.MILLISECONDS)
                        .build();
                OkHttpUtils.initClient(okHttpClient);
            }
            initCrashHandler();
        }
    }

    /**
     * 初始化程序崩溃捕捉处理
     */
    protected void initCrashHandler() {
        CustomActivityOnCrash.install(this);
//        CrashHandler handler = CrashHandler.getInstance();
//        handler.init(getApplicationContext());
//        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    public static Context getContext() {
        return mContext;
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
