package com.fangzhi.app.download;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.fangzhi.app.MyApplication;
import com.fangzhi.app.tools.ScreenUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
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
    private Map<Integer,Bitmap> bitmapMap = new TreeMap<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    });
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
            runOnQueueCache(new DownLoadThread(key,mapUrl.get(key)));
        }
    }
    private void waitForComplete(int number, Bitmap bitmap){
        bitmapMap.put(number,bitmap);
        index++;
        if(index == mapUrl.size()){
            callBack.onDownLoadSuccess(bitmapMap);
            index = 0;
        }
    }
    public interface ImageDownLoadCallBack {
        void onDownLoadSuccess(Map<Integer,Bitmap> map);
    }

    private class DownLoadThread implements Runnable {
        private String url;
        private int index;
        public DownLoadThread(int index,String url){
            this.index = index;
            this.url = url;
        }
        @Override
        public void run() {
            try {
                final long startTime = System.nanoTime();  //開始時間
               Bitmap bitmap =  Glide.with(MyApplication.getContext())
                        .load(url)
                        .asBitmap()
                        .into(1280,720).get();
                final long consumingTime = System.nanoTime() - startTime; //消耗時間
                System.out.println("下载"+consumingTime / 1000/1000 + "毫秒");
                waitForComplete(index,bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
