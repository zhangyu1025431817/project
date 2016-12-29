package com.buyiren.app.main.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.buyiren.app.R;
import com.buyiren.app.bean.CellGraphResponseBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class PictureViewHolder extends BaseViewHolder<CellGraphResponseBean.CellGraph> {
    ImageView iv_image;
    TextView view_cover;

    public PictureViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_picture_view_pager);
        AutoUtils.autoSize(itemView);
        iv_image = $(R.id.iv_image);
        view_cover = $(R.id.view_cover);
    }

    @Override
    public void setData(CellGraphResponseBean.CellGraph data) {
        Glide.with(getContext())
                .load(data.getIMG_URL())
                .placeholder(R.drawable.bg_image_placeholder)
                .into(iv_image);
        if (data.isSelected()) {
            view_cover.setBackgroundResource(R.drawable.iv_cover_blue);
        } else {
            view_cover.setBackgroundResource(R.color.transparent);
        }

    }
}
