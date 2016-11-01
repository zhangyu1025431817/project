package com.fangzhi.app.download;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.os.Handler;

import com.bumptech.glide.Glide;
import com.fangzhi.app.tools.ScreenUtils;
import com.squareup.picasso.Picasso;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by smacr on 2016/9/25.
 */
public class DownLoadImageService {
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

    /**
     * 单线程列队执行
     */
    private static ExecutorService singleExecutor = null;

    /**
     * 执行单线程列队执行
     */
    public void runOnQueueSingle(Runnable runnable) {
        if (singleExecutor == null) {
            singleExecutor = Executors.newSingleThreadExecutor();

        }
        singleExecutor.submit(runnable);
    }

    /**
     * 异步线程计数器
     */
    private int mIndex;
    /**
     * 将bitmap缓存
     */
    private Map<Integer, Bitmap> bitmapMap = new TreeMap<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    });
    /**
     * 需要下载的数量
     */
    private int mSize;
    private OnDrawListener mListener;
    private Handler handler = new Handler();
    private int width;
    private int height;
    private boolean isHigh;
    private Context mContext;

    public DownLoadImageService(Map<Integer, String> mapUrl, Context context, OnDrawListener listener, boolean isHigh) {
        mListener = listener;
        this.isHigh = isHigh;
        mContext = context;
        width = ScreenUtils.getScreenWidth(context);
        height = ScreenUtils.getScreenHeight(context);
        drawAll(mapUrl);
    }

    /**
     * 第一次下载全部场景
     *
     * @param mapUrl
     */
    public void drawAll(Map<Integer, String> mapUrl) {
        mIndex = 0;
        this.mSize = mapUrl.size();
        for (Integer key : mapUrl.keySet()) {
            runOnQueueCache(new DownLoadThread(key, mapUrl.get(key)));
        }
    }


    /**
     * 仅变化一张
     *
     * @param number
     * @param url
     */
    public void drawOne(int number, String url, boolean isCancel) {
        if (isCancel) {
            bitmapMap.remove(number);
            runOnQueueSingle(new DrawImageThread());
        } else {
            runOnQueueCache(new DownLoadThread(number, url));
        }
    }

    /**
     * 清除所有场景
     */
    public void clearAll() {
        Bitmap bgBitmap = bitmapMap.get(0);
        bitmapMap.clear();
        bitmapMap.put(0, bgBitmap);
        runOnQueueSingle(new DrawImageThread());

    }

    /**
     * 等待所有线程全部下载完成
     *
     * @param number
     * @param bitmap
     */
    private void waitForComplete(int number, Bitmap bitmap) {
        bitmapMap.put(number, bitmap);
        mIndex++;
        if (mIndex == mSize) {
            //切换只需要变化一个
            mIndex = mIndex - 1;
            //全部下载完成开始画图
            runOnQueueSingle(new DrawImageThread());
        }
    }

    /**
     * 下载线程
     */
    private class DownLoadThread implements Runnable {
        private String url;
        private int index;

        public DownLoadThread(int index, String url) {
            this.index = index;
            this.url = url;
        }

        @Override
        public void run() {
            Bitmap bitmap = null;
            try {
                if (!isHigh) {
                    bitmap = Glide.with(mContext)
                            .load(url)
                            .asBitmap()
                            .into(1280, 720).get();
                } else {
                    bitmap = Picasso.with(mContext)
                            .load(url)
                            .get();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                waitForComplete(index, bitmap);
            }
        }
    }

    /**
     * 画图线程
     */
    private class DrawImageThread implements Runnable {

        @Override
        public void run() {
            final Bitmap mResultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(mResultBitmap);
            //抗锯齿
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
            try {
                for (Integer key : bitmapMap.keySet()) {
                    Bitmap bitmap = bitmapMap.get(key);
                    if (bitmap == null) {
                        continue;
                    }
                    Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());// 截取bmp1中的矩形区域
                    Rect dstRect = new Rect(0, 0, width, height);// bmp1在目标画布中的位置
                    canvas.drawBitmap(bitmap, srcRect, dstRect, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mListener.onDrawSucceed(mResultBitmap);
                    }
                });
            }
        }
    }


    public interface OnDrawListener {
        void onDrawSucceed(Bitmap bitmap);
    }
}
