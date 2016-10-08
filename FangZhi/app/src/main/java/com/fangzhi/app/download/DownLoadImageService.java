package com.fangzhi.app.download;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.fangzhi.app.MyApplication;
import com.fangzhi.app.tools.ScreenUtils;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by smacr on 2016/9/25.
 */
public class DownLoadImageService{
    /**
     * 多线程下载
     */
    private static ExecutorService cacheExecutor = null;
    /**
     * 执行多线程列队执行
     */
    public void runOnQueueCache(Runnable runnable) {
        if (cacheExecutor == null) {
            cacheExecutor = Executors.newCachedThreadPool();

        }
        cacheExecutor.submit(runnable);
    }
    private  int index;
    private Map<Integer,String> mapUrl;
    private ImageDownLoadCallBack callBack;
    private int width;
    private int height;
    public DownLoadImageService( ImageDownLoadCallBack callBack,Context context) {
        this.callBack = callBack;
        width = ScreenUtils.getScreenWidth(context);
        height = ScreenUtils.getScreenHeight(context);
    }

    public void startDown(Map<Integer,String> mapUrl){
        this.mapUrl = mapUrl;
        for(Integer key : mapUrl.keySet()){
            runOnQueueCache(new DownLoadThread(mapUrl.get(key)));
        }
    }
    private void waitForComplete(){
        index++;
        if(index == mapUrl.size()){
            callBack.onDownLoadSuccess();
            index = 0;
        }
    }
    public interface ImageDownLoadCallBack {
        void onDownLoadSuccess();
    }

    private class DownLoadThread implements Runnable {
        private String url;
        public DownLoadThread(String url){
            this.url = url;
        }
        @Override
        public void run() {
            try {
                final long startTime = System.nanoTime();  //開始時間
                Glide.with(MyApplication.getContext())
                        .load(url)
                        .asBitmap()
                        .into(width,height);
                final long consumingTime = System.nanoTime() - startTime; //消耗時間
                System.out.println("下载"+consumingTime / 1000/1000 + "毫秒");
                waitForComplete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
