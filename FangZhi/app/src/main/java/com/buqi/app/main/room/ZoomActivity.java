package com.buqi.app.main.room;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.buqi.app.MyApplication;
import com.buqi.app.tools.ScreenUtils;
import com.squareup.picasso.Picasso;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by smacr on 2016/10/11.
 */
public class ZoomActivity extends AppCompatActivity {
    private int width;
    private int height;
    private Map<Integer,String> map;
    Handler handler = new Handler();
    SubsamplingScaleImageView imageView;
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
     Bitmap mResultBitmap;
    Canvas canvas;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = new SubsamplingScaleImageView(this);
        setContentView(imageView);
        width = ScreenUtils.getScreenWidth(this);
        height = ScreenUtils.getScreenHeight(this);
        Bundle bundle = getIntent().getExtras();
        map = (Map<Integer, String>) bundle
                .get("map");
        mResultBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

        canvas = new Canvas(mResultBitmap);
        runOnQueueSingle(new DrawImageThread(map));
    }
    private class DrawImageThread implements Runnable {
        Map<Integer,String> map;
        public DrawImageThread(Map<Integer,String> map){
            this.map = map;
        }

        @Override
        public void run() {

            for (Integer key : map.keySet()) {
                try {
                    Bitmap bitmap = Picasso.with(MyApplication.getContext())
                            .load(map.get(key))
                            .get();
                    Log.e("Tag",bitmap.getWidth()+"--"+bitmap.getHeight());
                        Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());// 截取bmp1中的矩形区域
                         Rect dstRect = new Rect(0, 0, width, height);// bmp1在目标画布中的位置
                    canvas.drawBitmap(bitmap, srcRect,dstRect, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                   imageView.setImage(ImageSource.bitmap(mResultBitmap));
                  //  imageView.setImageBitmap(mResultBitmap);
                }
            });
        }
    }
}
