package com.fangzhi.app.main.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fangzhi.app.R;
import com.fangzhi.app.bean.HousesResponseBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by smacr on 2016/9/1.
 */
public class HousesViewHolder extends BaseViewHolder<HousesResponseBean.Houses> {
    ImageView imageView;
    TextView tvName;
    TextView tvCount;

    public HousesViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_houses);
        imageView = $(R.id.iv_image);
        tvName = $(R.id.tv_name);
    }

    @Override
    public void setData(HousesResponseBean.Houses data) {
        Glide.with(getContext())
                .load(data.getImagePath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .crossFade()
                .into(imageView);
    }
}
