package com.fangzhi.app.main.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fangzhi.app.R;
import com.fangzhi.app.bean.WindowType;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class WindowTypeViewHolder extends BaseViewHolder<WindowType> {
    ImageView ivScene;
    TextView tvName;

    public WindowTypeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_scene);
        AutoUtils.autoSize(itemView);
        ivScene = $(R.id.iv_image_scene);
        tvName = $(R.id.tv_name);
    }

    @Override
    public void setData(WindowType data) {
        tvName.setText(data.getDecorate_name());
        Glide.with(getContext())
                .load(data.getDecorate_img())
                .placeholder(R.drawable.bg_image_placeholder)
                .crossFade()
                .into(ivScene);

    }
}
