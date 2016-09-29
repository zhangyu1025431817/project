package com.fangzhi.app.download;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fangzhi.app.MyApplication;
import com.fangzhi.app.tools.ScreenUtils;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by smacr on 2016/9/29.
 */
public class DrawImageService  {
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
    private Map<Integer, String> mapUrl;
    private Handler handler;
    private Canvas canvas;
    private ImageView imageView;
    private Bitmap resultBitmap;
    private OnDrawListener listener;
    private int width;
    private int height;

    public DrawImageService(Context context,
                            ImageView imageView,
                            Handler handler,OnDrawListener listener) {

        this.imageView = imageView;
        this.handler = handler;
        width = ScreenUtils.getScreenWidth(context);
        height = ScreenUtils.getScreenHeight(context);
        this.listener = listener;
        resultBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(resultBitmap);
    }

    public void startDraw(Map<Integer, String> mapUrl){
        this.mapUrl = mapUrl;
        runOnQueueSingle(new DrawImageThread());
    }
    private class DrawImageThread implements Runnable{

        @Override
        public void run() {
            for (Integer key : mapUrl.keySet()) {
                try {
                    Bitmap bitmap = Glide.with(MyApplication.getContext())
                            .load(mapUrl.get(key))
                            .asBitmap()
                            .into(ScreenUtils.getScreenWidth(MyApplication.getContext()) / 2,
                                    ScreenUtils.getScreenHeight(MyApplication.getContext()) / 2)
                            .get();
                    Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());// 截取bmp1中的矩形区域
                    Rect dstRect = new Rect(0, 0, width, height);// bmp1在目标画布中的位置
                    canvas.drawBitmap(bitmap, srcRect, dstRect, null);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(resultBitmap);
                    listener.onDrawSucceed();
                }
            });
        }
    }
    public interface OnDrawListener{
        void onDrawSucceed();
    }
}
