package com.fangzhi.app.main.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhi.app.R;
import com.fangzhi.app.bean.CategoryPart;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class HomeCategoryTypePartViewHolder extends BaseViewHolder<CategoryPart.Part> {
    TextView textView;
    ImageView imageView;

    public HomeCategoryTypePartViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_home_category_part_product);
        AutoUtils.autoSize(itemView);
        textView = $(R.id.tv_name);
        imageView = $(R.id.iv_image);
    }

    @Override
    public void setData(CategoryPart.Part data) {
        textView.setText(data.getPart_name());
        Glide.with(getContext())
                .load(data.getPart_img_short())
                .placeholder(R.drawable.bg_image_placeholder)
                .crossFade()
                .into(imageView);
    }
}
