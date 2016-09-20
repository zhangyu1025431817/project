package com.fangzhi.app.main.room;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fangzhi.app.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smacr on 2016/9/12.
 */
public class RoomActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    ImageView imageView;
    private Bitmap resultBitmap;
    int[] drawables = new int[]{R.drawable.bg_lv_1, R.drawable.bg_lv_2, R.drawable.bg_lv_3, R.drawable.bg_lv_4,
            R.drawable.bg_lv_5, R.drawable.bg_lv_6, R.drawable.bg_lv_7, R.drawable.bg_lv_8
    };
    int count = 0;
    String[] urls = new String[]{"http://120.76.212.114/test/bg_lv_1.webp",
            "http://120.76.212.114/test/bg_lv_2.webp",
            "http://120.76.212.114/test/bg_lv_3.webp",
            "http://120.76.212.114/test/bg_lv_4.webp",
            "http://120.76.212.114/test/bg_lv_5.webp",
            "http://120.76.212.114/test/bg_lv_6.webp",
            "http://120.76.212.114/test/bg_lv_7.webp",
            "http://120.76.212.114/test/bg_lv_8.webp",};
    SparseArray<Bitmap> spArray = new SparseArray<>();
    List<Model> list = new ArrayList<>();
    Canvas canvas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    Fresco.initialize(this);
        setContentView(R.layout.activity_room);
        frameLayout = (FrameLayout) findViewById(R.id.layout_frame);

        //     imageView.setImageBitmap(resultBitmap);
        for (int i = 0; i < urls.length; i++) {
     //       downLoad(urls[i]);
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this)
                    .load(urls[i])
                    .skipMemoryCache(true)
                    .override(1000, 750)
                    .into(iv);
            frameLayout.addView(iv, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        }

//        for (int i = 0; i < urls.length; i++) {
//            downLoad(urls[i]);
//        }

    }

    /*
   * 使用Canvas合并Bitmap
   */
//    private Bitmap mergeBitmap() {
//        // 获取ImageView上得Bitmap图片
//        // 创建空得背景bitmap
//        // 生成画布图像
//        Bitmap resultBitmap = Bitmap.createBitmap(imageView.getWidth(),
//                imageView.getHeight(), Bitmap.Config.RGB_565);
//        Canvas canvas = new Canvas(resultBitmap);// 使用空白图片生成canvas
//        for (int i = 0; i < list.size(); i++) {
////            Bitmap bmp;
////            if(isChange && i==5){
////                bmp  = BitmapFactory.decodeResource(getResources(),R.drawable.bg_lv_6_1);
////            }else{
////                Date curDate = new Date(System.currentTimeMillis());
////                bmp  = BitmapFactory.decodeResource(getResources(),drawables[i]);
////                Date endDate = new Date(System.currentTimeMillis());
////                long diff = endDate.getTime() - curDate.getTime();
////                Log.e("生成bitmap所用时间",diff+"");
////            }
//            Rect srcRect = new Rect(0, 0, list.get(i).getWidth(), list.get(i).getHeight());// 截取bmp1中的矩形区域
//            Rect dstRect = new Rect(0, 0, imageView.getWidth(),
//                    imageView.getHeight());// bmp1在目标画布中的位置
//
//            canvas.drawBitmap(list.get(i), srcRect, dstRect, null);
//
//            // bmp.recycle();
//        }
//        isChange = !isChange;
//        //   canvas.save( Canvas.ALL_SAVE_FLAG );
//        // 将bmp1,bmp2合并显示
//        return resultBitmap;
//    }


    private void downLoad(String url) {
        Glide.with(this).load(url).asBitmap().into(new SimpleTarget<Bitmap>(1280, 720) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Log.e("onResourceReady", "宽=" + resource.getWidth() + "，高=" + resource.getHeight());

                Rect srcRect = new Rect(0, 0, resource.getWidth(), resource.getHeight());// 截取bmp1中的矩形区域
                Rect dstRect = new Rect(0, 0, imageView.getWidth(), imageView.getHeight());// bmp1在目标画布中的位置
                canvas.drawBitmap(resource, srcRect, dstRect, null);
                count++;
                if (count == 8) {
                    //排序
                    imageView.setImageBitmap(resultBitmap);
                }
            }

        });
    }

    /**
     * 图片质量压缩
     *
     * @param image
     * @param srcPath 要保存的路径
     * @return
     */
    public static Bitmap compressImage(Bitmap image, String srcPath) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.WEBP, 80, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {    // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        try {
            FileOutputStream out = new FileOutputStream(srcPath);
            bitmap.compress(Bitmap.CompressFormat.WEBP, 90, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    class Model {
        int index;
        int res;

        public Model(int index, int res) {
            this.index = index;
            this.res = res;
        }
    }
}
