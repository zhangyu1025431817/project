package com.buqi.app.main.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.buqi.app.R;
import com.buqi.app.bean.Scene;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class SameSceneViewHolder extends BaseViewHolder<Scene> {
    TextView textView;
    ImageView imageView;


    public SameSceneViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_room_scene);
        AutoUtils.autoSize(itemView);
        textView = $(R.id.tv_name);
        imageView = $(R.id.iv_scene);

    }

    @Override
    public void setData(final Scene data) {
        textView.setText(data.getScene_name());
        if(data.isSelected()){
            imageView.setBackground(getContext().getResources().getDrawable(R.drawable.sample_scene_bg_p));
        }else{
            imageView.setBackground(getContext().getResources().getDrawable(R.drawable.sample_scene_bg_n));
        }
        Glide.with(getContext())
                .load(data.getScene_img())
                .placeholder(R.drawable.bg_image_placeholder)
                .crossFade()
                .into(imageView);

    }

}
