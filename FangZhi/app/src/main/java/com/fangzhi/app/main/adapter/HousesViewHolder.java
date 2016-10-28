package com.fangzhi.app.main.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhi.app.R;
import com.fangzhi.app.bean.Houses;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class HousesViewHolder extends BaseViewHolder<Houses.House> {
    ImageView imageView;
    TextView tvName;
    TextView tvCount;

    public HousesViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_houses);
        AutoUtils.autoSize(itemView);
        imageView = $(R.id.iv_image);
        tvName = $(R.id.tv_name);
        tvCount = $(R.id.tv_count);
    }

    @Override
    public void setData(Houses.House data) {
        tvName.setText(data.getPre_cname());
        tvCount.setText("户型:"+data.getCount());
        Glide.with(getContext())
                .load(data.getPre_img())
                .placeholder(R.drawable.bg_image_placeholder)
                .crossFade()
                .into(imageView);
    }

}
