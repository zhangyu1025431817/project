package com.fangzhi.app;

import android.app.ActivityManager;
import android.content.Context;

import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.litepal.LitePalApplication;

import java.util.List;
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
        String processName = getProcessName(this, android.os.Process.myPid());
        if (processName != null) {
            if (processName.equals("com.fangzhi.app")) {
                AutoLayoutConifg.getInstance().useDeviceSize();
                //配置
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new LoggerInterceptor("TAG", true))
                        .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                        .readTimeout(10000L, TimeUnit.MILLISECONDS)
                        .build();
                OkHttpUtils.initClient(okHttpClient);
                mContext = getApplicationContext();
            }
        }
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
