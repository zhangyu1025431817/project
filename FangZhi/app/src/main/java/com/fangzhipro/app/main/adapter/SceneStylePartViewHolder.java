
package com.fangzhipro.app.main.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhipro.app.R;
import com.fangzhipro.app.bean.Scene;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class SceneStylePartViewHolder extends BaseViewHolder<Scene> {
    ImageView iv_image;
    TextView tv_name;
    public SceneStylePartViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_scene_style);
        AutoUtils.autoSize(itemView);
        tv_name = $(R.id.tv_name);
        iv_image = $(R.id.iv_image);
    }

    @Override
    public void setData(Scene data) {
        tv_name.setText(data.getScene_name());
        Glide.with(getContext())
                .load(data.getScene_img())
                .placeholder(R.drawable.bg_image_placeholder)
                .crossFade()
                .into(iv_image);
    }
}
