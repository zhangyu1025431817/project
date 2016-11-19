package com.fangzhi.app.main.ddd;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fangzhi.app.R;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.List;

public class BannerAdapter extends StaticPagerAdapter {

    private Context ctx;
    private List<String> list;
       public BannerAdapter(Context ctx , List<String> list){
           this.ctx = ctx;
           this.list = list;
        }

        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(ctx);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //加载图片
            Glide.with(ctx)
                    .load(list.get(position))
                    .placeholder(R.drawable.bg_image_placeholder)
                    .into(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }