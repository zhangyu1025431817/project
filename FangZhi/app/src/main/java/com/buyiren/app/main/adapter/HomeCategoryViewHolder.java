package com.buyiren.app.main.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.buyiren.app.R;
import com.buyiren.app.bean.SellType;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class HomeCategoryViewHolder extends BaseViewHolder<SellType.Category> {
    ImageView iv_image;
    TextView tv_cover;

    public HomeCategoryViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_home_category);
        AutoUtils.autoSize(itemView);
        iv_image = $(R.id.iv_image);
        tv_cover = $(R.id.tv_cover);
    }

    @Override
    public void setData(SellType.Category data) {
        Glide.with(getContext())
                .load(data.getCate_img())
                .placeholder(R.drawable.bg_image_placeholder)
                .crossFade()
                .into(iv_image);
        if (data.getIs_use() == 0) {
            tv_cover.setVisibility(View.VISIBLE);
        } else {
            tv_cover.setVisibility(View.GONE);
        }
    }
}
