package com.fangzhi.app.main.adapter;

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
    private OnItemClickListener mListener;
       public BannerAdapter(Context ctx , List<String> list,OnItemClickListener listener){
           this.ctx = ctx;
           this.list = list;
           mListener = listener;
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
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(position);
                }
            });
            return imageView;
        }

        @Override
        public int getCount() {
            return list.size();
        }

    public interface OnItemClickListener{
        void onClick(int position);
         }
    }
