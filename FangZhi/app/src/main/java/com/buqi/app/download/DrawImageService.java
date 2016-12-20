package com.buqi.app.download;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.widget.ImageView;

import com.buqi.app.tools.ScreenUtils;

import java.util.Map;
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
    private Map<Integer, Bitmap> mapUrl;
    private Handler handler;
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
    }

    public void startDraw(Map<Integer, Bitmap> mapUrl){
        this.mapUrl = mapUrl;
        runOnQueueSingle(new DrawImageThread());
    }
    private class DrawImageThread implements Runnable{

        @Override
        public void run() {
           Canvas canvas = new Canvas(resultBitmap);
            for (Integer key : mapUrl.keySet()) {
                try {
                   final long startTime = System.nanoTime();  //開始時間
//                    Bitmap bitmap = Glide.with(MyApplication.getContext())
//                            .load(mapUrl.get(key))
//                            .asBitmap()
//                            .into(1280,720)
//                            .get();
                    Bitmap bitmap = mapUrl.get(key);
                    final long consumingTime = System.nanoTime() - startTime; //消耗時間
                    System.out.println("获取bitmap"+consumingTime / 1000/1000 + "毫秒");
                    Matrix mMatrix = new Matrix();
                    mMatrix.postScale(width/(float) bitmap.getWidth(),
                            height/(float) bitmap.getHeight());
                //    Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());// 截取bmp1中的矩形区域
               //     Rect dstRect = new Rect(0, 0, width, height);// bmp1在目标画布中的位置
                    canvas.drawBitmap(bitmap, mMatrix, null);
                } catch (Exception e) {
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
