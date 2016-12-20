package com.buqi.app.main.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.buqi.app.R;
import com.buqi.app.bean.HouseTypes;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class HouseTypeViewHolder extends BaseViewHolder<HouseTypes.HouseType> {
    ImageView imageView;
    TextView tvName;
    TextView tvArea;

    public HouseTypeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_house_type);
        AutoUtils.autoSize(itemView);
        imageView = $(R.id.iv_image);
        tvName = $(R.id.tv_name);
        tvArea = $(R.id.tv_area);
    }

    @Override
    public void setData(HouseTypes.HouseType data) {
        tvName.setText(data.getHouse_name());
        tvArea.setText(data.getHouse_room());
        Glide.with(getContext())
                .load(data.getHouse_img())
                .placeholder(R.drawable.bg_image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(imageView);
    }
}
