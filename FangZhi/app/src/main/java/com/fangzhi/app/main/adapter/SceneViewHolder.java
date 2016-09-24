package com.fangzhi.app.main.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fangzhi.app.R;
import com.fangzhi.app.bean.Scene;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by smacr on 2016/9/1.
 */
public class SceneViewHolder extends BaseViewHolder<Scene> {
    ImageView ivScene;
    ImageView ivStructure;
    TextView tvName;

    public SceneViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_scene);
        ivScene = $(R.id.iv_image_scene);
        ivStructure = $(R.id.iv_image_structure);
        tvName = $(R.id.tv_name);
    }

    @Override
    public void setData(Scene data) {
        tvName.setText(data.getScene_name());
//        tvArea.setText(data.getHouse_room());
        Glide.with(getContext())
                .load(data.getScene_img())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(ivScene);
        Glide.with(getContext())
                .load(data.getShow_img())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(ivStructure);
    }
}
