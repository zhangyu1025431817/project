package com.fangzhi.app.main.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fangzhi.app.R;
import com.fangzhi.app.bean.RoomProduct;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by smacr on 2016/9/1.
 */
public class PartViewHolder extends BaseViewHolder<RoomProduct> {
    ImageView iv_product;
    View view_cover;

    public PartViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_product);
        iv_product = $(R.id.iv_product);
        view_cover = $(R.id.view_cover);
    }

    @Override
    public void setData(RoomProduct data) {
        Glide.with(getContext())
                .load(data.getPart_img_short())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .crossFade()
                .into(iv_product);
        if(data.isSelected()){
            view_cover.setVisibility(View.VISIBLE);
        }else{
            view_cover.setVisibility(View.GONE);
        }

    }
}
