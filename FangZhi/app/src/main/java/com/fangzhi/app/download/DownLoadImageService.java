package com.fangzhi.app.download;

import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.fangzhi.app.MyApplication;
import com.fangzhi.app.tools.ScreenUtils;

/**
 * Created by smacr on 2016/9/25.
 */
public class DownLoadImageService implements Runnable {
    private String url;
    private ImageDownLoadCallBack callBack;

    public DownLoadImageService(String url, ImageDownLoadCallBack callBack) {
        this.url = url;
        this.callBack = callBack;
    }

    @Override
    public void run() {
        Bitmap file = null;
        try {
            final long startTime = System.nanoTime();  //開始時間
            file = Glide.with(MyApplication.getContext())
                    .load(url)
                    .asBitmap()
                    .into(ScreenUtils.getScreenWidth(MyApplication.getContext())/2,
                            ScreenUtils.getScreenHeight(MyApplication.getContext())/2)
                    .get();
            long consumingTime = System.nanoTime() - startTime; //消耗時間
            System.out.println(consumingTime / 1000 + "微秒");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (file != null) {
                callBack.onDownLoadSuccess(file);
            } else {
                callBack.onDownLoadFailed();
            }
        }
    }

    public interface ImageDownLoadCallBack {

        void onDownLoadSuccess(Bitmap file);

        void onDownLoadFailed();
    }
}
