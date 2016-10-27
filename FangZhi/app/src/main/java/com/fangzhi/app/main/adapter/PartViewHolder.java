package com.fangzhi.app.main.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhi.app.R;
import com.fangzhi.app.bean.RoomProduct;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class PartViewHolder extends BaseViewHolder<RoomProduct> {
    ImageView iv_product;
    TextView view_cover;

    public PartViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_product);
        AutoUtils.autoSize(itemView);
        iv_product = $(R.id.iv_product);
        view_cover = $(R.id.view_cover);
    }

    @Override
    public void setData(RoomProduct data) {
        Glide.with(getContext())
                .load(data.getPart_img_short().split(";")[0])
                .into(iv_product);
        view_cover.setText(data.getPart_name());
        if(data.isSelected()){
            view_cover.setBackgroundResource(R.drawable.iv_cover);
        }else{
            view_cover.setBackgroundResource(R.color.transparent);
        }

    }
}
