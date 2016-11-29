package com.fangzhi.app.main.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fangzhi.app.R;
import com.fangzhi.app.bean.CategoryPart;
import com.fangzhi.app.main.decoration.product.PictureActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class HomeCategoryTypePartViewHolder extends BaseViewHolder<CategoryPart.Part> {
    TextView textView;
    ImageView imageView;
    RelativeLayout layoutPart;
    Button btnDiy;
    Button btnStyle;

    public HomeCategoryTypePartViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_home_category_part_product);
        AutoUtils.autoSize(itemView);
        layoutPart = $(R.id.layout_part);
        btnDiy = $(R.id.btn_diy);
        btnStyle = $(R.id.btn_style);
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
        btnDiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), PictureActivity.class));
            }
        });
        if (data.isSelected()) {
            btnDiy.setBackground(getContext().getResources().getDrawable(R.drawable.btn_blue));
            btnDiy.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
            btnStyle.setBackground(getContext().getResources().getDrawable(R.drawable.btn_blue));
            btnStyle.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
            btnStyle.setClickable(true);
            btnDiy.setClickable(true);
        } else {
            btnDiy.setBackground(getContext().getResources().getDrawable(R.drawable.btn_corner_transpant_black));
            btnDiy.setTextColor(getContext().getResources().getColor(R.color.black_3c3c3c));
            btnStyle.setBackground(getContext().getResources().getDrawable(R.drawable.btn_corner_transpant_black));
            btnStyle.setTextColor(getContext().getResources().getColor(R.color.black_3c3c3c));
            btnStyle.setClickable(false);
            btnDiy.setClickable(false);
        }
    }
}
