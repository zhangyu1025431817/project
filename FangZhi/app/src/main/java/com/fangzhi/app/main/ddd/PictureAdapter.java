package com.fangzhi.app.main.ddd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fangzhi.app.R;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;
import java.util.Map;

public class PictureAdapter extends StaticPagerAdapter {

    private Context ctx;
    private ArrayList<Map<String,String>> list;
       public PictureAdapter(Context ctx , ArrayList<Map<String,String>>  list){
           this.ctx = ctx;
           this.list = list;
        }

        @Override
        public View getView(ViewGroup container, final int position) {
            return LayoutInflater.from(ctx).inflate(R.layout.item_2d_picture,null);
        }

        @Override
        public int getCount() {
            return list.size();
        }

    @Override
    public void onBind(View view, int position) {
        Map<String,String> map = list.get(position);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
        Glide.with(ctx)
            .load(map.get("url"))
            .placeholder(R.drawable.bg_image_placeholder)
            .centerCrop()
            .into(imageView);
    }
}
