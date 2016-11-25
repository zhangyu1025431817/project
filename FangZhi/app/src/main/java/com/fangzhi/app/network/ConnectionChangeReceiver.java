package com.fangzhi.app.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fangzhi.app.tools.T;

/**
 * Created by smacr on 2016/11/23.
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo  wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            T.showLong(context, "网络不可以用,请检查网络后再试!");
            //改变背景或者 处理网络的全局变量
        }else {
            //改变背景或者 处理网络的全局变量
        }
    }
}
